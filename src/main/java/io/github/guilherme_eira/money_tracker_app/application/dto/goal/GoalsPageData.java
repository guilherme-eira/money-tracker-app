package io.github.guilherme_eira.money_tracker_app.application.dto.goal;

import org.springframework.data.domain.Page;

import java.time.LocalDate;

public record GoalsPageData(
        Page<GoalCardOutput> goals,
        LocalDate startDate
) {
}
