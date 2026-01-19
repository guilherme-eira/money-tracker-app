package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.exception.TransactionNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class DeleteTransactionUseCaseTest {

    @Mock
    TransactionRepository repository;

    @InjectMocks
    DeleteTransactionUseCase deleteTransactionUseCase;

    @Test
    void shouldThrowExceptionWhenTransactionIsNotFound(){
        var transactionId = UUID.randomUUID();

        BDDMockito.given(repository.findById(transactionId)).willReturn(Optional.empty());

        Assertions.assertThrows(TransactionNotFoundException.class, () -> {
            deleteTransactionUseCase.execute(transactionId);
        });
    }
}