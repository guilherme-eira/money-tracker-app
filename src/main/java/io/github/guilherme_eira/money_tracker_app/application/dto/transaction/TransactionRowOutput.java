package io.github.guilherme_eira.money_tracker_app.application.dto.transaction;

import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionRowOutput(
        UUID id,
        LocalDate date,
        String description,
        String categoryName,
        TransactionType type,
        BigDecimal value
) {
}
