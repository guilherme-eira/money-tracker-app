package io.github.guilherme_eira.money_tracker_app.application.dto.goal;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateGoalInput(
        UUID goalId,
        BigDecimal maxExpense
) {
}
