package io.github.guilherme_eira.money_tracker_app.application.dto.goal;

import java.math.BigDecimal;
import java.util.UUID;

public record GoalCardOutput(
        UUID id,
        String categoryName,
        BigDecimal currentAmount,
        BigDecimal maxAmount,
        BigDecimal progressPercent,
        Boolean isOverBudget,
        BigDecimal remainingAmount
) {
}
