package io.github.guilherme_eira.money_tracker_app.application.dto.goal;

import java.util.UUID;

public record GetGoalsPageDataInput(
        UUID userId,
        Integer month,
        Integer year,
        Integer pageSize,
        Integer page
) {
}
