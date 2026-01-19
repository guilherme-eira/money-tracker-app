package io.github.guilherme_eira.money_tracker_app.infra.gateway;

import io.github.guilherme_eira.money_tracker_app.application.gateway.GoalRepository;
import io.github.guilherme_eira.money_tracker_app.domain.model.Goal;
import io.github.guilherme_eira.money_tracker_app.infra.mapper.GoalMapper;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.repository.GoalJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GoalRepositoryImpl implements GoalRepository {

    private final GoalJpaRepository repository;
    private final GoalMapper mapper;

    @Override
    public void save(Goal goal) {
        var goalEntity = mapper.toEntity(goal);
        repository.save(goalEntity);
    }

    @Override
    public Optional<Goal> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<Goal> findGoalsByUserIdAndStartDateBetween(UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return repository.findGoalsByUserIdAndStartDateBetween(userId, startDate, endDate, pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Boolean existsByUserIdAndCategoryIdAndStartDateBetween(UUID userId, UUID categoryId, LocalDate startDate, LocalDate endDate) {
        return repository.existsByUserIdAndCategoryIdAndStartDateBetween(userId, categoryId, startDate, endDate);
    }

    @Override
    public void delete(Goal goal) {
        repository.delete(mapper.toEntity(goal));
    }

    @Override
    public void deleteByUserId(UUID id) {
        repository.deleteByUserId(id);
    }

}
