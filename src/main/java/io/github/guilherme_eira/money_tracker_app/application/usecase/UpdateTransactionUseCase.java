package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.dto.transaction.UpdateTransactionInput;
import io.github.guilherme_eira.money_tracker_app.application.exception.CategoryNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.exception.TransactionNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.CategoryRepository;
import io.github.guilherme_eira.money_tracker_app.application.gateway.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateTransactionUseCase{

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void execute(UpdateTransactionInput input) {
        var transaction = transactionRepository.findById(input.transactionId()).orElseThrow(TransactionNotFoundException::new);
        var category = categoryRepository.findById(input.categoryId()).orElseThrow(CategoryNotFoundException::new);

        transaction.setDescription(input.description());
        transaction.setCategory(category);
        transaction.setValue(input.value());
        transaction.setDate(input.date());
        transaction.setUpdatedAt(LocalDateTime.now());

        transactionRepository.save(transaction);
    }
}
