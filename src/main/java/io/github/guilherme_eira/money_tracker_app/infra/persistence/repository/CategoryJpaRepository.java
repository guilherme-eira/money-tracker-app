package io.github.guilherme_eira.money_tracker_app.infra.persistence.repository;

import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID> {
    List<CategoryEntity> findByTransactionType(TransactionType type);
}
