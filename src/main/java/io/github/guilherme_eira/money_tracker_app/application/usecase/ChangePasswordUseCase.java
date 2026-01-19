package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.dto.user.ChangePasswordInput;
import io.github.guilherme_eira.money_tracker_app.application.exception.PasswordDoesNotMatchActualException;
import io.github.guilherme_eira.money_tracker_app.application.exception.UserNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.PasswordEncoderGateway;
import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChangePasswordUseCase {

    private final UserRepository repository;
    private final PasswordEncoderGateway encoder;

    @Transactional
    public void execute(ChangePasswordInput input){
        var user = repository.findById(input.userId()).orElseThrow(UserNotFoundException::new);

        if (!encoder.matches(input.currentPassword(), user.getPassword())){
            throw new PasswordDoesNotMatchActualException();
        }

        var hashedPassword = encoder.encode(input.newPassword());
        user.setPassword(hashedPassword);
        user.setUpdatedAt(LocalDateTime.now());

        repository.save(user);
    }
}
