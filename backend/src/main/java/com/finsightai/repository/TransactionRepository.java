package com.finsightai.repository;
import com.finsightai.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.time.LocalDate; import java.util.*;
public interface TransactionRepository extends JpaRepository<Transaction,Long>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> findByTransactionDateBetween(LocalDate from, LocalDate to);
    List<Transaction> findByFlaggedTrueOrderByTransactionDateDesc();
    List<Transaction> findByCategoryIgnoreCase(String category);
    Optional<Transaction> findTopByOrderByAmountDesc();
    long countByTypeIgnoreCase(String type);
}