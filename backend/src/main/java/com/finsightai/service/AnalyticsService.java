package com.finsightai.service;

import com.finsightai.dto.*;
import com.finsightai.model.Transaction;
import com.finsightai.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final TransactionRepository repo;

    public AnalyticsService(TransactionRepository repo) {
        this.repo = repo;
    }

    public KpiResponse kpis() {
        List<Transaction> a = repo.findAll();

        BigDecimal rev = sum(a, "INCOME");
        BigDecimal exp = sum(a, "EXPENSE");
        BigDecimal profit = rev.subtract(exp);

        double margin = rev.signum() == 0
                ? 0
                : profit.doubleValue() * 100 / rev.doubleValue();

        return new KpiResponse(
                rev,
                exp,
                profit,
                round(margin),
                a.size(),
                (int) repo.count((r, q, cb) ->
                        cb.isTrue(r.<Boolean>get("flagged")))
        );
    }

    private BigDecimal sum(List<Transaction> x, String t) {
        return x.stream()
                .filter(v -> t.equalsIgnoreCase(v.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<CategoryMetric> categorySpend() {
        return repo.findAll()
                .stream()
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Transaction::getAmount,
                                BigDecimal::add
                        )
                ))
                .entrySet()
                .stream()
                .map(e -> new CategoryMetric(
                        e.getKey(),
                        e.getValue()
                ))
                .sorted((a, b) ->
                        b.amount().compareTo(a.amount()))
                .toList();
    }

    public List<AnomalyResponse> anomalies() {

        List<Transaction> a = repo.findAll();

        Map<String, List<Double>> groups = a.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.mapping(
                                t -> t.getAmount().doubleValue(),
                                Collectors.toList()
                        )
                ));

        List<AnomalyResponse> out = new ArrayList<>();

        for (Transaction t : a) {

            List<Double> v = groups.get(t.getCategory());

            double mean = v.stream()
                    .mapToDouble(x -> x)
                    .average()
                    .orElse(0);

            double sd = Math.sqrt(
                    v.stream()
                            .mapToDouble(x ->
                                    Math.pow(x - mean, 2))
                            .average()
                            .orElse(0)
            );

            double z = sd == 0
                    ? 0
                    : (t.getAmount().doubleValue() - mean) / sd;

            boolean flagged =
                    Math.abs(z) >= 2.0 ||
                    t.getAmount().compareTo(
                            BigDecimal.valueOf(75000)
                    ) > 0;

            t.setFlagged(flagged);
            t.setAnomalyScore(round(z));

            t.setAnomalyReason(
                    flagged
                            ? "Unusual amount relative to category baseline"
                            : "Within expected range"
            );

            if (flagged) {
                out.add(
                        new AnomalyResponse(
                                t.getId(),
                                t.getCategory(),
                                t.getAmount(),
                                round(z),
                                t.getAnomalyReason(),
                                Math.abs(z) >= 3
                                        ? "CRITICAL"
                                        : "HIGH"
                        )
                );
            }
        }

        repo.saveAll(a);

        return out.stream()
                .sorted((x, y) ->
                        Double.compare(
                                Math.abs(y.score()),
                                Math.abs(x.score())
                        )
                )
                .toList();
    }

    public List<ForecastPoint> forecast(int months) {

        List<Transaction> a = repo.findAll();

        Map<YearMonth, BigDecimal> monthly =
                a.stream()
                        .filter(t ->
                                "INCOME".equalsIgnoreCase(
                                        t.getType()
                                )
                        )
                        .collect(Collectors.groupingBy(
                                t -> YearMonth.from(
                                        t.getTransactionDate()
                                ),
                                Collectors.reducing(
                                        BigDecimal.ZERO,
                                        Transaction::getAmount,
                                        BigDecimal::add
                                )
                        ));

        YearMonth now = YearMonth.now();

        List<BigDecimal> hist = new ArrayList<>();

        for (int i = 5; i >= 0; i--) {
            hist.add(
                    monthly.getOrDefault(
                            now.minusMonths(i),
                            BigDecimal.ZERO
                    )
            );
        }

        double avg = hist.stream()
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .orElse(0);

        List<ForecastPoint> out = new ArrayList<>();

        for (int i = 1; i <= months; i++) {
            out.add(
                    new ForecastPoint(
                            now.plusMonths(i).toString(),
                            round(avg),
                            "6-month moving average"
                    )
            );
        }

        return out;
    }

    public double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    public List<Map<String, Object>> monthlyTrend() {

        Map<YearMonth, Map<String, BigDecimal>> m =
                new TreeMap<>();

        for (Transaction t : repo.findAll()) {

            YearMonth ym =
                    YearMonth.from(t.getTransactionDate());

            m.computeIfAbsent(
                    ym,
                    k -> new HashMap<>()
            ).merge(
                    t.getType(),
                    t.getAmount(),
                    BigDecimal::add
            );
        }

        List<Map<String, Object>> result =
                new ArrayList<>();

        for (Map.Entry<YearMonth, Map<String, BigDecimal>> e
                : m.entrySet()) {

            Map<String, Object> row =
                    new LinkedHashMap<>();

            row.put(
                    "month",
                    e.getKey().toString()
            );

            row.put(
                    "revenue",
                    e.getValue().getOrDefault(
                            "INCOME",
                            BigDecimal.ZERO
                    )
            );

            row.put(
                    "expenses",
                    e.getValue().getOrDefault(
                            "EXPENSE",
                            BigDecimal.ZERO
                    )
            );

            result.add(row);
        }

        return result;
    }
}