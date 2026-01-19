package io.github.guilherme_eira.money_tracker_app.application.dto.overview;

import java.math.BigDecimal;

public record GoalSummaryOutput(
        String categoryName,
        BigDecimal currentAmount,
        BigDecimal maxExpense,
        BigDecimal progressPercent
) {
}
