package io.github.guilherme_eira.money_tracker_app.application.dto.overview;

import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionSummaryOutput(
        String description,
        BigDecimal value,
        TransactionType type,
        LocalDate date
) {
}
