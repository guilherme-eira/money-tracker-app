package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.dto.user.ChangePasswordInput;
import io.github.guilherme_eira.money_tracker_app.application.exception.PasswordDoesNotMatchActualException;
import io.github.guilherme_eira.money_tracker_app.application.exception.UserNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.PasswordEncoderGateway;
import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
import io.github.guilherme_eira.money_tracker_app.domain.model.User;
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
class ChangePasswordUseCaseTest {

    @InjectMocks
    ChangePasswordUseCase changePasswordUseCase;

    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoderGateway encoder;

    @Test
    void shouldThrowExceptionWhenPasswordsDoNotMatch(){
        var userId = UUID.randomUUID();
        var user = new User();
        user.setPassword("hashedPassword");
        var input = new ChangePasswordInput(
                userId,
                "rawPassword",
                "newPassword"
        );

        BDDMockito.given(repository.findById(userId)).willReturn(Optional.of(user));
        BDDMockito.given(encoder.matches("rawPassword", "hashedPassword")).willReturn(false);

        Assertions.assertThrows(PasswordDoesNotMatchActualException.class, () -> {
            changePasswordUseCase.execute(input);
        });
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound(){
        var userId = UUID.randomUUID();
        var input = new ChangePasswordInput(
                userId,
                "rawPassword",
                "newPassword"
        );

        BDDMockito.given(repository.findById(userId)).willReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            changePasswordUseCase.execute(input);
        });
    }

}