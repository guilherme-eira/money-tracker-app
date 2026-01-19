package io.github.guilherme_eira.money_tracker_app.application.gateway;

import io.github.guilherme_eira.money_tracker_app.domain.model.Goal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface GoalRepository {
    void save(Goal goal);
    Optional<Goal> findById(UUID id);
    Page<Goal> findGoalsByUserIdAndStartDateBetween(UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
    Boolean existsByUserIdAndCategoryIdAndStartDateBetween(UUID userId, UUID categoryId, LocalDate startDate, LocalDate endDate);
    void delete(Goal goal);
    void deleteByUserId(UUID id);
}
