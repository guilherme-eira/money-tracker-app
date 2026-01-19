package io.github.guilherme_eira.money_tracker_app.application.dto.transaction;

import java.util.UUID;

public record GetTransactionsPageDataInput(
        UUID userId,
        UUID categoryId,
        String description,
        Integer month,
        Integer year,
        Integer pageSize,
        Integer page
) {}
