package io.github.guilherme_eira.money_tracker_app.application.query;

import io.github.guilherme_eira.money_tracker_app.application.dto.common.TransactionFilters;
import io.github.guilherme_eira.money_tracker_app.application.dto.overview.OverviewPageData;
import io.github.guilherme_eira.money_tracker_app.application.gateway.GoalRepository;
import io.github.guilherme_eira.money_tracker_app.application.gateway.TransactionRepository;
import io.github.guilherme_eira.money_tracker_app.application.mapper.GoalOutputMapper;
import io.github.guilherme_eira.money_tracker_app.application.mapper.TransactionOutputMapper;
import io.github.guilherme_eira.money_tracker_app.domain.model.Goal;
import io.github.guilherme_eira.money_tracker_app.domain.model.Transaction;
import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetOverviewPageDataQuery {

    private final TransactionRepository transactionRepository;
    private final GoalRepository goalRepository;
    private final TransactionOutputMapper transactionMapper;
    private final GoalOutputMapper goalMapper;

    public OverviewPageData execute(UUID userId) {

        var pageable = PageRequest.of(0, 4, Sort.by("date").descending());
        var startOfMonth = LocalDate.now().withDayOfMonth(1);
        var endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
        LocalDate startOfLastWeek = LocalDate.now().minusWeeks(1)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endOfLastWeek = startOfLastWeek.plusDays(6);

        var baseFilter = new TransactionFilters(
                userId,
                null,
                null,
                null,
                startOfMonth,
                endOfMonth
        );

        BigDecimal income = transactionRepository.getTotalByFilters(filterWithType(baseFilter, TransactionType.INCOME));
        BigDecimal expenses = transactionRepository.getTotalByFilters(filterWithType(baseFilter, TransactionType.EXPENSE));
        BigDecimal balance = income.subtract(expenses);
        Page<Transaction> transactions = transactionRepository.findTransactionsByFilter(baseFilter, pageable);
        Page<Goal> goals = goalRepository.findGoalsByUserIdAndStartDateBetween(userId, startOfMonth, endOfMonth,
                PageRequest.of(0,
                        4, Sort.by("startDate").descending()));
        Map<String, BigDecimal> expensesByCategory = transactionRepository.getTotalExpensesByCategory(userId,
                startOfMonth,
                endOfMonth);
        Map<Integer, BigDecimal> expensesByDayOfWeek = transactionRepository.getTotalExpensesByDayOfWeek(userId,
                startOfLastWeek, endOfLastWeek);

        String expensesByCategoryJson = transactionMapper.toExpensesByCategoryJson(expensesByCategory);
        String expensesByDayOfWeekJson = transactionMapper.toExpensesByDayOfWeekJson(expensesByDayOfWeek);

        return new OverviewPageData(
                income,
                expenses,
                balance,
                transactionMapper.toTransactionSummaryOutput(transactions),
                goalMapper.toGoalSummaryOutput(goals, expensesByCategory),
                expensesByCategoryJson,
                expensesByDayOfWeekJson
        );
    }

    private TransactionFilters filterWithType(TransactionFilters base, TransactionType type) {
        return new TransactionFilters(
                base.userId(),
                base.categoryId(),
                type,
                base.description(),
                base.startDate(),
                base.endDate()
        );
    }


}