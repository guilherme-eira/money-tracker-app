package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.dto.transaction.CreateTransactionInput;
import io.github.guilherme_eira.money_tracker_app.application.exception.CategoryNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.exception.UserNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.CategoryRepository;
import io.github.guilherme_eira.money_tracker_app.application.gateway.TransactionRepository;
import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
import io.github.guilherme_eira.money_tracker_app.domain.model.Transaction;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateTransactionUseCase{

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void execute(CreateTransactionInput input) {

        var user = userRepository.findById(input.userId())
                .orElseThrow(UserNotFoundException::new);
        var category = categoryRepository.findById(input.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        var transaction = new Transaction(
                UUID.randomUUID(),
                input.description(),
                input.value(),
                category.getTransactionType(),
                input.date(),
                LocalDateTime.now(),
                null,
                user,
                category
        );

        transactionRepository.save(transaction);
    }
}
