package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.exception.GoalNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.exception.UserNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.GoalRepository;
import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
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
class DeleteUserUseCaseTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    DeleteUserUseCase deleteUserUseCase;

    @Test
    void shouldThrowExceptionWhenUserIsNotFound(){
        var userId = UUID.randomUUID();

        BDDMockito.given(repository.findById(userId)).willReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            deleteUserUseCase.execute(userId);
        });
    }
}