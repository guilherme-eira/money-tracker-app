package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.dto.auth.ResetPasswordInput;
import io.github.guilherme_eira.money_tracker_app.application.exception.ExpiredTokenException;
import io.github.guilherme_eira.money_tracker_app.application.exception.InvalidTokenException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
import io.github.guilherme_eira.money_tracker_app.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ResetPasswordUseCaseTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    ResetPasswordUseCase resetPasswordUseCase;

    @Test
    void shouldThrowExceptionWhenTokenIsNotFound(){
        var token = UUID.randomUUID();
        var input = new ResetPasswordInput(
                token.toString(),
                "password"
        );

        BDDMockito.given(repository.findByPasswordResetToken(token)).willReturn(Optional.empty());

        Assertions.assertThrows(InvalidTokenException.class, () -> {
            resetPasswordUseCase.execute(input);
        });
    }

    @Test
    void shouldThrowExceptionWhenTokenIsInvalid(){
        var user = new User();
        user.setTokenExpiration(LocalDateTime.now().minusMinutes(10));
        var token = UUID.randomUUID();
        var input = new ResetPasswordInput(
                token.toString(),
                "password"
        );

        BDDMockito.given(repository.findByPasswordResetToken(token)).willReturn(Optional.of(user));

        Assertions.assertThrows(ExpiredTokenException.class, () -> {
            resetPasswordUseCase.execute(input);
        });
    }
}