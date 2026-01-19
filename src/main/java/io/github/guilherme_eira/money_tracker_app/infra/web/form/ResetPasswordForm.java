package io.github.guilherme_eira.money_tracker_app.infra.web.form;

import io.github.guilherme_eira.money_tracker_app.infra.web.validation.PasswordMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@PasswordMatch(
        passwordField = "newPassword",
        confirmPasswordField = "newPasswordConfirmation",
        message = "As senhas devem ser iguais!"
)
public record ResetPasswordForm(
        String token,
        @NotBlank(message = "O campo 'Nova Senha' é obrigatório")
        @Size(
                min = 8,
                max = 100,
                message = "A senha deve ter entre 8 e 100 caracteres"
        )
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*\\d).*$",
                message = "A senha deve conter pelo menos uma letra e um número"
        )
        String newPassword,
        @NotBlank(message = "O campo 'Confirme Sua Senha' é obrigatório")
        @Size(max = 100)
        String newPasswordConfirmation
) {
}
