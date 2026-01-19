package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.dto.transaction.CreateTransactionInput;
import io.github.guilherme_eira.money_tracker_app.application.exception.CategoryNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.exception.UserNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.CategoryRepository;
import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
import io.github.guilherme_eira.money_tracker_app.domain.model.User;
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
class CreateTransactionUseCaseTest {

    @Mock
    UserRepository userRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CreateTransactionUseCase createTransactionUseCase;

    @Test
    void shouldThrowExceptionWhenUserIsNotFound() {
        var categoryId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var input = new CreateTransactionInput(
                "description",
                categoryId,
                BigDecimal.valueOf(100),
                LocalDate.now(),
                userId
        );

        BDDMockito.given(userRepository.findById(userId)).willReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            createTransactionUseCase.execute(input);
        });
    }

    @Test
    void shouldThrowExceptionWhenCategoryIsNotFound() {
        var user = new User();
        var userId = UUID.randomUUID();
        var categoryId = UUID.randomUUID();
        var input = new CreateTransactionInput(
                "description",
                categoryId,
                BigDecimal.valueOf(100),
                LocalDate.now(),
                userId
        );

        BDDMockito.given(userRepository.findById(userId)).willReturn(Optional.of(user));
        BDDMockito.given(categoryRepository.findById(categoryId)).willReturn(Optional.empty());

        Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            createTransactionUseCase.execute(input);
        });
    }

}