package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.exception.UserNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.dto.user.UpdateUserInput;
import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase{

    private final UserRepository repository;

    @Transactional
    public void execute(UpdateUserInput input) {
        var user = repository.findById(input.userId()).orElseThrow(UserNotFoundException::new);

        user.setName(input.name());
        user.setUpdatedAt(LocalDateTime.now());

        repository.save(user);
    }
}
