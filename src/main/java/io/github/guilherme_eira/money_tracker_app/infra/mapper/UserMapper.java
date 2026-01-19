package io.github.guilherme_eira.money_tracker_app.infra.mapper;

import io.github.guilherme_eira.money_tracker_app.domain.model.User;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {
    UserEntity toEntity(User domain);
    User toDomain(UserEntity domain);
}