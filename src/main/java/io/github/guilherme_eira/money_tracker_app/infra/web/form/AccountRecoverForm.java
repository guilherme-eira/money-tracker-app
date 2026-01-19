package io.github.guilherme_eira.money_tracker_app.infra.web.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountRecoverForm(
        @NotBlank(message = "Preencha o email")
        @Email(message = "Preencha com um email válido")
        @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
        String email
) {
}
