package io.github.guilherme_eira.money_tracker_app.infra.mapper;

import io.github.guilherme_eira.money_tracker_app.domain.model.Category;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryEntity toEntity(Category domain);
    Category toDomain(CategoryEntity entity);
}
