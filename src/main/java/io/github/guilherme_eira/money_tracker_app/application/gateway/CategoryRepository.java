package io.github.guilherme_eira.money_tracker_app.application.gateway;

import io.github.guilherme_eira.money_tracker_app.domain.model.Category;
import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
    Optional<Category> findById(UUID categoryId);
    List<Category> findAllCategories();
    List<Category> findCategoriesByTransactionType(TransactionType transactionType);
}
