package io.github.guilherme_eira.money_tracker_app.application.gateway;

import io.github.guilherme_eira.money_tracker_app.application.dto.common.TransactionFilters;
import io.github.guilherme_eira.money_tracker_app.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {
    void save(Transaction transaction);
    Optional<Transaction> findById(UUID id);
    Page<Transaction> findTransactionsByFilter(TransactionFilters filters, Pageable pageable);
    BigDecimal getTotalByFilters(TransactionFilters filters);
    Map<String, BigDecimal> getTotalExpensesByCategory(UUID userId, LocalDate startDate, LocalDate endDate);
    Map<Integer, BigDecimal> getTotalExpensesByDayOfWeek(UUID userId, LocalDate startDate, LocalDate endDate);
    void delete(Transaction transaction);
    void deleteByUserId(UUID id);

}
