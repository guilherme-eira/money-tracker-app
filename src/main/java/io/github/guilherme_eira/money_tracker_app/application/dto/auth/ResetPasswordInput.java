package io.github.guilherme_eira.money_tracker_app.application.dto.auth;

public record ResetPasswordInput(
        String token,
        String password
) {
}
