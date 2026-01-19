package io.github.guilherme_eira.money_tracker_app.infra.gateway;

import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;
import io.github.guilherme_eira.money_tracker_app.domain.model.Category;
import io.github.guilherme_eira.money_tracker_app.application.gateway.CategoryRepository;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.repository.CategoryJpaRepository;
import io.github.guilherme_eira.money_tracker_app.infra.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository repository;
    private final CategoryMapper mapper;

    @Override
    public Optional<Category> findById(UUID categoryId) {
        return repository.findById(categoryId).map(mapper::toDomain);
    }

    @Override
    public List<Category> findAllCategories() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Category> findCategoriesByTransactionType(TransactionType transactionType) {
        return repository.findByTransactionType(transactionType).stream().map(mapper::toDomain).toList();
    }
}
