package io.github.guilherme_eira.money_tracker_app.application.dto.common;

import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;

import java.time.LocalDate;
import java.util.UUID;

public record TransactionFilters(
        UUID userId,
        UUID categoryId,
        TransactionType type,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
