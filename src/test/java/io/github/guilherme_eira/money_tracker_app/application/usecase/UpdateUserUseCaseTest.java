package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.dto.user.CreateUserInput;
import io.github.guilherme_eira.money_tracker_app.application.dto.user.UpdateUserInput;
import io.github.guilherme_eira.money_tracker_app.application.exception.UserAlreadyExistsException;
import io.github.guilherme_eira.money_tracker_app.application.exception.UserNotFoundException;
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
class UpdateUserUseCaseTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UpdateUserUseCase updateUserUseCase;

    @Test
    void shouldThrowExceptionWhenUserIsNotFound(){
        var userId = UUID.randomUUID();
        var input = new UpdateUserInput(
                userId,
                "name"
        );

        BDDMockito.given(repository.findById(userId)).willReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            updateUserUseCase.execute(input);
        });
    }

}