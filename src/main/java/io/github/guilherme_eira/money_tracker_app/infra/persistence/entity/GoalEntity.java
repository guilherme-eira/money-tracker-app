package io.github.guilherme_eira.money_tracker_app.infra.persistence.entity;

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
@Table(name = "goals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoalEntity {
    @Id
    private UUID id;
    private BigDecimal maxExpense;
    private LocalDate startDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private CategoryEntity category;
}
