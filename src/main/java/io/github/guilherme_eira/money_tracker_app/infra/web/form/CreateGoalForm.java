package io.github.guilherme_eira.money_tracker_app.infra.web.form;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

public record CreateGoalForm(
        @NotNull(message = "O campo 'Categoria' é obrigatório")
        UUID categoryId,
        @NotNull(message = "O campo 'Despesa Máxima Esperada' é obrigatório")
        @Positive(message = "A despesa máxima esperada deve ser um valor maior que zero")
        @Digits(
                integer = 17,
                fraction = 2,
                message = "A despesa máxima deve ter até 17 dígitos inteiros e 2 casas decimais"
        )
        BigDecimal maxExpense,
        @NotNull(message = "O campo 'Início' da Meta é obrigatório")
        YearMonth startDate
) {
}
