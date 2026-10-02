package com.finsightai.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name="transactions", indexes={@Index(name="idx_tx_date",columnList="transaction_date"),@Index(name="idx_tx_category",columnList="category"),@Index(name="idx_tx_source",columnList="source_system")})
public class Transaction {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotNull @Column(name="transaction_date",nullable=false) private LocalDate transactionDate;
    @NotBlank @Column(nullable=false) private String account;
    @NotBlank @Column(nullable=false) private String category;
    @NotBlank @Column(nullable=false) private String type;
    @NotNull @DecimalMin("0.01") @Column(nullable=false,precision=15,scale=2) private BigDecimal amount;
    private String department;
    private String description;
    private String sourceSystem;
    @Column(nullable=false) private boolean flagged;
    private String anomalyReason;
    private Double anomalyScore;
    @Column(nullable=false,updatable=false) private java.time.Instant createdAt;
    @PrePersist void onCreate(){createdAt=java.time.Instant.now();}
    public Long getId(){return id;} public void setId(Long v){id=v;} public LocalDate getTransactionDate(){return transactionDate;} public void setTransactionDate(LocalDate v){transactionDate=v;} public String getAccount(){return account;} public void setAccount(String v){account=v;} public String getCategory(){return category;} public void setCategory(String v){category=v;} public String getType(){return type;} public void setType(String v){type=v;} public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal v){amount=v;} public String getDepartment(){return department;} public void setDepartment(String v){department=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public String getSourceSystem(){return sourceSystem;} public void setSourceSystem(String v){sourceSystem=v;} public boolean isFlagged(){return flagged;} public void setFlagged(boolean v){flagged=v;} public String getAnomalyReason(){return anomalyReason;} public void setAnomalyReason(String v){anomalyReason=v;} public Double getAnomalyScore(){return anomalyScore;} public void setAnomalyScore(Double v){anomalyScore=v;} public java.time.Instant getCreatedAt(){return createdAt;}
}