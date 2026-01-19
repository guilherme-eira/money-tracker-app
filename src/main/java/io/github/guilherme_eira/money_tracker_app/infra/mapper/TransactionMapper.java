package io.github.guilherme_eira.money_tracker_app.infra.mapper;

import io.github.guilherme_eira.money_tracker_app.infra.persistence.entity.TransactionEntity;
import io.github.guilherme_eira.money_tracker_app.domain.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoryMapper.class})
public interface TransactionMapper {
    TransactionEntity toEntity(Transaction domain);
    Transaction toDomain(TransactionEntity entity);
}
