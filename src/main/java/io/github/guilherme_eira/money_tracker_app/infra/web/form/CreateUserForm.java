package io.github.guilherme_eira.money_tracker_app.infra.web.form;

import io.github.guilherme_eira.money_tracker_app.infra.web.validation.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@PasswordMatch(
        passwordField = "password",
        confirmPasswordField = "passwordConfirmation",
        message = "As senhas devem ser iguais"
)
public record CreateUserForm(
        @NotBlank(message = "O campo 'Nome' é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String name,
        @NotBlank(message = "O campo 'CPF' é obrigatório")
        @Pattern(
                regexp = "^\\d{11}$",
                message = "CPF deve conter exatamente 11 dígitos numéricos"
        )
        String document,
        @NotBlank(message = "O campo 'Email' é obrigatório")
        @Email(message = "o email deve ter um formato válido")
        @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
        String email,
        @NotBlank(message = "O campo 'Senha' é obrigatório")
        @Size(
                min = 8,
                max = 100,
                message = "A senha deve ter entre 8 e 100 caracteres"
        )
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*\\d).*$",
                message = "A senha deve conter pelo menos uma letra e um número"
        )
        String password,
        @NotBlank(message = "O campo 'Confirme Sua Senha' é obrigatório")
        @Size(max = 100)
        String passwordConfirmation
) {
}
