package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.dto.transaction.UpdateTransactionInput;
import io.github.guilherme_eira.money_tracker_app.application.exception.CategoryNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.exception.GoalNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.exception.TransactionNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.CategoryRepository;
import io.github.guilherme_eira.money_tracker_app.application.gateway.TransactionRepository;
import io.github.guilherme_eira.money_tracker_app.domain.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UpdateTransactionUseCaseTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    UpdateTransactionUseCase updateTransactionUseCase;

    @Test
    void shouldThrowExceptionWhenTransactionIsNotFound(){
        var transactionId = UUID.randomUUID();
        var categoryId = UUID.randomUUID();
        var input = new UpdateTransactionInput(
                transactionId,
                "description",
                categoryId,
                BigDecimal.valueOf(100),
                LocalDate.now()
        );

        BDDMockito.given(transactionRepository.findById(transactionId)).willReturn(Optional.empty());

        Assertions.assertThrows(TransactionNotFoundException.class, () -> {
            updateTransactionUseCase.execute(input);
        });
    }

    @Test
    void shouldThrowExceptionWhenCategoryIsNotFound(){
        var transaction = new Transaction();
        var transactionId = UUID.randomUUID();
        var categoryId = UUID.randomUUID();
        var input = new UpdateTransactionInput(
                transactionId,
                "description",
                categoryId,
                BigDecimal.valueOf(100),
                LocalDate.now()
        );

        BDDMockito.given(transactionRepository.findById(transactionId)).willReturn(Optional.of(transaction));
        BDDMockito.given(categoryRepository.findById(categoryId)).willReturn(Optional.empty());

        Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            updateTransactionUseCase.execute(input);
        });
    }
}