package io.github.guilherme_eira.money_tracker_app.application.dto.user;

import java.util.UUID;

public record ChangePasswordInput(
        UUID userId,
        String currentPassword,
        String newPassword
) {
}
