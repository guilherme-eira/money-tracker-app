package io.github.guilherme_eira.money_tracker_app.application.dto.goal;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalDetailsOutput(
        String categoryName,
        BigDecimal maxExpense,
        LocalDate startDate
) {
}
