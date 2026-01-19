package io.github.guilherme_eira.money_tracker_app.infra.persistence.dto;

import java.math.BigDecimal;

public record CategoryExpense(
        String categoryName,
        BigDecimal expenses
) {
}
