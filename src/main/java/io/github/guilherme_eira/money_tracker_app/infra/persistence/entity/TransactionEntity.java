package io.github.guilherme_eira.money_tracker_app.infra.persistence.entity;

import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionEntity {
    @Id
    private UUID id;
    private String description;
    @Column(name = "`value`", nullable = false)
    private BigDecimal value;
    @Column(name = "transaction_date")
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private CategoryEntity category;
}
