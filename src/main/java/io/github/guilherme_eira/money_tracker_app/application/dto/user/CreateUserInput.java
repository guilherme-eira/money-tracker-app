package io.github.guilherme_eira.money_tracker_app.application.dto.user;

public record CreateUserInput(
        String name,
        String document,
        String email,
        String password
) {}
