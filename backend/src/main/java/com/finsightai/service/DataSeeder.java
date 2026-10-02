package com.finsightai.service;

import com.finsightai.model.AppUser;
import com.finsightai.model.Transaction;
import com.finsightai.repository.AppUserRepository;
import com.finsightai.repository.TransactionRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TransactionRepository tx;
    private final AppUserRepository users;
    private final PasswordEncoder encoder;

    public DataSeeder(
            TransactionRepository tx,
            AppUserRepository users,
            PasswordEncoder encoder) {

        this.tx = tx;
        this.users = users;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {

        seedDemoUser(
                "admin",
                "Admin@123",
                "ADMIN"
        );

        seedDemoUser(
                "analyst",
                "Analyst@123",
                "FINANCE_ANALYST"
        );

        if (tx.count() > 0) {
            return;
        }

        String[] categories = {
                "Software",
                "Cloud",
                "Payroll",
                "Marketing",
                "Sales",
                "Operations"
        };

        for (int i = 0; i < 96; i++) {

            Transaction t = new Transaction();

            t.setTransactionDate(
                    LocalDate.now().minusDays(i * 2)
            );

            t.setCategory(
                    categories[i % categories.length]
            );

            t.setAccount(
                    i % 2 == 0
                            ? "Operating"
                            : "Corporate"
            );

            t.setType(
                    i % 4 == 0
                            ? "INCOME"
                            : "EXPENSE"
            );

            double amount =
                    i % 4 == 0
                            ? 42000 + (i % 7) * 1800
                            : 6500 + (i % 11) * 700;

            if (i == 17) {
                amount = 125000;
            }

            if (i == 58) {
                amount = 98000;
            }

            t.setAmount(
                    BigDecimal.valueOf(amount)
            );

            t.setDepartment(
                    i % 3 == 0
                            ? "Engineering"
                            : i % 3 == 1
                            ? "Finance"
                            : "Sales"
            );

            t.setSourceSystem(
                    i % 3 == 0
                            ? "SAP"
                            : i % 3 == 1
                            ? "Oracle"
                            : "Dynamics"
            );

            t.setDescription(
                    "ERP imported transaction #" + (10000 + i)
            );

            tx.save(t);
        }
    }

    private void seedDemoUser(
            String username,
            String password,
            String role) {

        AppUser user = users.findAll()
                .stream()
                .filter(u -> username.equals(u.getUsername()))
                .findFirst()
                .orElse(null);

        if (user == null) {

            user = new AppUser();
            user.setUsername(username);
        }

        user.setPassword(
                encoder.encode(password)
        );

        user.setRole(role);

        users.save(user);
    }
}