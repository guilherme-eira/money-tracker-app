package io.github.guilherme_eira.money_tracker_app.application.dto.user;

import java.util.UUID;

public record UpdateUserInput(
        UUID userId,
        String name
) {
}
