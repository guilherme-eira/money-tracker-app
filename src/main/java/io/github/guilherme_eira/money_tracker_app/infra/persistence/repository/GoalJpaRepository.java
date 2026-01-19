package io.github.guilherme_eira.money_tracker_app.infra.persistence.repository;

import io.github.guilherme_eira.money_tracker_app.infra.persistence.entity.GoalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.UUID;

public interface GoalJpaRepository extends JpaRepository<GoalEntity, UUID> {
    Page<GoalEntity> findGoalsByUserIdAndStartDateBetween(UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
    Boolean existsByUserIdAndCategoryIdAndStartDateBetween(UUID userId, UUID categoryId, LocalDate startDate, LocalDate endDate);
    @Modifying
    @Query("DELETE FROM GoalEntity g WHERE g.user.id = :id")
    void deleteByUserId(UUID id);
}
