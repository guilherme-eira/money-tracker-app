package io.github.guilherme_eira.money_tracker_app.application.dto.overview;

import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public record OverviewPageData(
        BigDecimal userMonthIncome,
        BigDecimal userMonthExpenses,
        BigDecimal userCurrentBalance,
        Page<TransactionSummaryOutput> userRecentTransactions,
        Page<GoalSummaryOutput> userGoalsSummary,
        String expensesByCategoryJson,
        String expensesByDayOfWeekJson
) {
}
