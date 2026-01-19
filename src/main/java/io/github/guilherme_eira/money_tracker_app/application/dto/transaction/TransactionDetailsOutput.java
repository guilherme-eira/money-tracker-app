package io.github.guilherme_eira.money_tracker_app.application.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionDetailsOutput(
        String description,
        UUID categoryId,
        BigDecimal value,
        LocalDate date
) {
}
