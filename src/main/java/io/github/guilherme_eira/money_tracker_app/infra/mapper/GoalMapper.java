package io.github.guilherme_eira.money_tracker_app.infra.mapper;

import io.github.guilherme_eira.money_tracker_app.domain.model.Goal;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.entity.GoalEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoryMapper.class})
public interface GoalMapper {
    GoalEntity toEntity(Goal domain);
    Goal toDomain(GoalEntity entity);
}
