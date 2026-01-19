package io.github.guilherme_eira.money_tracker_app.infra.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserForm {

        @NotBlank(message = "O campo 'Nome' é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        private String name;
        private String document;
        private String email;
}