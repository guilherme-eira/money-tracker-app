package io.github.guilherme_eira.money_tracker_app.infra.persistence.repository;

import io.github.guilherme_eira.money_tracker_app.infra.persistence.dto.DayExpense;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.entity.TransactionEntity;
import io.github.guilherme_eira.money_tracker_app.application.dto.common.TransactionFilters;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.dto.CategoryExpense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, UUID> {

    @Query("""
                SELECT COALESCE(SUM(t.value), 0)
                FROM TransactionEntity t
                JOIN t.user u
                JOIN t.category c
                WHERE (:#{#filter.userId} IS NULL OR u.id = :#{#filter.userId})
                  AND (:#{#filter.categoryId} IS NULL OR c.id = :#{#filter.categoryId})
                  AND (:#{#filter.type} IS NULL OR t.transactionType = :#{#filter.type})
                  AND (:#{#filter.description} IS NULL OR t.description = :#{#filter.description})
                  AND (:#{#filter.startDate} IS NULL OR t.date >= :#{#filter.startDate})
                  AND (:#{#filter.endDate} IS NULL OR t.date <= :#{#filter.endDate})
            """)
    BigDecimal sumByFilters(@Param("filter") TransactionFilters filters);

    @Query("""
                SELECT t
                FROM TransactionEntity t
                JOIN t.user u
                JOIN t.category c
                WHERE (:#{#filter.userId} IS NULL OR u.id = :#{#filter.userId})
                  AND (:#{#filter.categoryId} IS NULL OR c.id = :#{#filter.categoryId})
                  AND (:#{#filter.type} IS NULL OR t.transactionType = :#{#filter.type})
                  AND (:#{#filter.description} IS NULL OR t.description = :#{#filter.description})
                  AND (:#{#filter.startDate} IS NULL OR t.date >= :#{#filter.startDate})
                  AND (:#{#filter.endDate} IS NULL OR t.date <= :#{#filter.endDate})
            """)
    Page<TransactionEntity> findByFilters(@Param("filter") TransactionFilters filters, Pageable pageable);

    @Query("""
                SELECT new io.github.guilherme_eira.money_tracker_app.infra.persistence.dto.CategoryExpense(
                    t.category.name,
                    SUM(t.value)
                )
                FROM TransactionEntity t
                WHERE t.user.id = :userId
                AND t.transactionType = 'EXPENSE'
                AND t.date BETWEEN :startDate AND :endDate
                GROUP BY t.category.id
                ORDER BY SUM(t.value) DESC
            """)
    List<CategoryExpense> sumExpensesGroupedByCategory(UUID userId, LocalDate startDate, LocalDate endDate);

    @Query("""
                SELECT new io.github.guilherme_eira.money_tracker_app.infra.persistence.dto.DayExpense(
                    FUNCTION('DAYOFWEEK', t.date),
                    SUM(t.value)
                )
                FROM TransactionEntity t
                WHERE t.user.id = :userId
                AND t.transactionType = 'EXPENSE'
                AND t.date BETWEEN :startDate AND :endDate
                GROUP BY FUNCTION('DAYOFWEEK', t.date)
                ORDER BY FUNCTION('DAYOFWEEK', t.date) ASC
            """)
    List<DayExpense> sumExpensesGroupedByDayOfWeek(UUID userId, LocalDate startDate, LocalDate endDate);

    @Modifying
    @Query("DELETE FROM TransactionEntity t WHERE t.user.id = :id")
    void deleteByUserId(UUID id);
}
