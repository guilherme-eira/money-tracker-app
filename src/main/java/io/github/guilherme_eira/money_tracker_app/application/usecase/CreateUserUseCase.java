package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.exception.UserAlreadyExistsException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.PasswordEncoderGateway;
import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
import io.github.guilherme_eira.money_tracker_app.application.dto.user.CreateUserInput;
import io.github.guilherme_eira.money_tracker_app.domain.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase{

    private final UserRepository userRepository;
    private final PasswordEncoderGateway encoder;

    @Transactional
    public void execute(CreateUserInput input) {

        if(userRepository.existsByDocumentOrEmail(input.document(), input.email())) {
            throw new UserAlreadyExistsException();
        };

        var user = new User(
                UUID.randomUUID(),
                input.name(),
                input.document(),
                input.email(),
                null,
                null,
                null,
                LocalDateTime.now(),
                null
        );

        String hashedPassword = encoder.encode(input.password());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }
}
