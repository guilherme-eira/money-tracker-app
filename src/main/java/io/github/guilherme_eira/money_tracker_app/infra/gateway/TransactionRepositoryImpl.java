package io.github.guilherme_eira.money_tracker_app.infra.gateway;

import io.github.guilherme_eira.money_tracker_app.domain.model.Transaction;
import io.github.guilherme_eira.money_tracker_app.application.dto.common.TransactionFilters;
import io.github.guilherme_eira.money_tracker_app.application.gateway.TransactionRepository;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.dto.CategoryExpense;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.dto.DayExpense;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.repository.TransactionJpaRepository;
import io.github.guilherme_eira.money_tracker_app.infra.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionJpaRepository repository;
    private final TransactionMapper mapper;

    @Override
    public void save(Transaction transaction) {
        var transactionEntity = mapper.toEntity(transaction);
        repository.save(transactionEntity);
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<Transaction> findTransactionsByFilter(TransactionFilters filters, Pageable pageable) {
        var filteredTransactions = repository.findByFilters(filters, pageable);
        return filteredTransactions.map(mapper::toDomain);
    }

    @Override
    public BigDecimal getTotalByFilters(TransactionFilters filters) {
        return repository.sumByFilters(filters);
    }

    @Override
    public Map<String, BigDecimal> getTotalExpensesByCategory(UUID userId, LocalDate startDate, LocalDate endDate) {
        List<CategoryExpense> expenses = repository.sumExpensesGroupedByCategory(userId, startDate, endDate);
        var totals = new HashMap<String, BigDecimal>();
        for (CategoryExpense item : expenses) {
            totals.put(item.categoryName(), item.expenses());
        }
        return totals;
    }

    @Override
    public Map<Integer, BigDecimal> getTotalExpensesByDayOfWeek(UUID userId, LocalDate startDate, LocalDate endDate) {
        List<DayExpense> expenses = repository.sumExpensesGroupedByDayOfWeek(userId, startDate, endDate);
        var totals = new HashMap<Integer, BigDecimal>();
        for (DayExpense item : expenses) {
            totals.put(item.dayOfWeek(), item.expenses());
        }
        return totals;
    }

    @Override
    public void delete(Transaction transaction) {
        repository.delete(mapper.toEntity(transaction));
    }

    @Override
    public void deleteByUserId(UUID id) {
        repository.deleteByUserId(id);
    }
}
