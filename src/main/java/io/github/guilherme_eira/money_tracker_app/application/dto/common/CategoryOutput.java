package io.github.guilherme_eira.money_tracker_app.application.dto.common;

import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;

import java.util.UUID;

public record CategoryOutput(
        UUID id,
        String categoryName,
        TransactionType type
) {
}
