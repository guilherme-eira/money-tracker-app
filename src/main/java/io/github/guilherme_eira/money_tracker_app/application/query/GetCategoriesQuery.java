package io.github.guilherme_eira.money_tracker_app.application.query;

import io.github.guilherme_eira.money_tracker_app.application.dto.common.CategoryOutput;
import io.github.guilherme_eira.money_tracker_app.application.gateway.CategoryRepository;
import io.github.guilherme_eira.money_tracker_app.domain.model.Category;
import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCategoriesQuery {

    private final CategoryRepository categoryRepository;

    public List<CategoryOutput> execute(TransactionType type) {

        if (type == null){
            return toOutput(categoryRepository.findAllCategories());
        }
        return toOutput(categoryRepository.findCategoriesByTransactionType(type));
    }

    private List<CategoryOutput> toOutput(List<Category> categories){
        return categories.stream().map(c -> {
            return new CategoryOutput(
                    c.getId(),
                    c.getName(),
                    c.getTransactionType()
            );
        }).toList();
    }
}
