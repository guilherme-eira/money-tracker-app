package io.github.guilherme_eira.money_tracker_app.application.dto.goal;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

public record CreateGoalInput(
        UUID categoryId,
        BigDecimal maxExpense,
        YearMonth startDate,
        UUID userId
) {}
