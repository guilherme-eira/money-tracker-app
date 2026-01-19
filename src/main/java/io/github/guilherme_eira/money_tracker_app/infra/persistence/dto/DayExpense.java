package io.github.guilherme_eira.money_tracker_app.infra.persistence.dto;

import java.math.BigDecimal;

public record DayExpense(
    Integer dayOfWeek,
    BigDecimal expenses
) {
    public DayExpense(Object dayOfWeek, BigDecimal expenses) {
        this(
                dayOfWeek instanceof Number n ? n.intValue() : null,
                expenses
        );
    }

}
