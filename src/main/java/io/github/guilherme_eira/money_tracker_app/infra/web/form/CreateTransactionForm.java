package io.github.guilherme_eira.money_tracker_app.infra.web.form;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateTransactionForm(
        @NotBlank(message = "O campo 'Descrição' é obrigatório")
        @Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres")
        String description,
        @NotNull(message = "O campo 'Categoria' é obrigatório")
        UUID categoryId,
        @NotNull(message = "O campo 'Valor' é obrigatório")
        @Positive(message = "O 'Valor' deve ser maior que zero")
        @Digits(
                integer = 17,
                fraction = 2,
                message = "O valor deve ter até 17 dígitos inteiros e 2 casas decimais"
        )
        BigDecimal value,
        @NotNull(message = "O campo 'Data' é obrigatório")
        @PastOrPresent(message = "A data deve ser passada ou presente")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date
) {
}
