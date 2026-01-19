package io.github.guilherme_eira.money_tracker_app.infra.persistence.entity;

import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryEntity {
    @Id
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
