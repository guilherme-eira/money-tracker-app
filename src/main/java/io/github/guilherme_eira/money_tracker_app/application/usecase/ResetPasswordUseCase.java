package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.dto.auth.ResetPasswordInput;
import io.github.guilherme_eira.money_tracker_app.application.exception.ExpiredTokenException;
import io.github.guilherme_eira.money_tracker_app.application.exception.InvalidTokenException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.PasswordEncoderGateway;
import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResetPasswordUseCase {

    private final UserRepository repository;
    private final PasswordEncoderGateway encoder;

    @Transactional
    public void execute(ResetPasswordInput input){
        var user = repository.findByPasswordResetToken(UUID.fromString(input.token())).orElseThrow(InvalidTokenException::new);

        if (user.getTokenExpiration().isBefore(LocalDateTime.now())){
            throw new ExpiredTokenException();
        }

        var hashedPassword = encoder.encode(input.password());
        user.setPassword(hashedPassword);
        user.setPasswordResetToken(null);
        user.setTokenExpiration(null);
        user.setUpdatedAt(LocalDateTime.now());

        repository.save(user);
    }
}
