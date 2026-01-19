package io.github.guilherme_eira.money_tracker_app.application.mapper;

import io.github.guilherme_eira.money_tracker_app.application.dto.common.CategoryOutput;
import io.github.guilherme_eira.money_tracker_app.domain.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryOutputMapper {

    public List<CategoryOutput> toCategoryOutput(List<Category> categories) {
        return categories.stream()
                .map(c -> new CategoryOutput(c.getId(), c.getName(), c.getTransactionType()))
                .toList();
    }
}
