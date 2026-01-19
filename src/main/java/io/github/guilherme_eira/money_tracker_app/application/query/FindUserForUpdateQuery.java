package io.github.guilherme_eira.money_tracker_app.application.query;

import io.github.guilherme_eira.money_tracker_app.application.dto.user.UserOutput;
import io.github.guilherme_eira.money_tracker_app.application.exception.UserNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindUserForUpdateQuery{

    private final UserRepository repository;

    public UserOutput execute(UUID id) {
        var user =  repository.findById(id).orElseThrow(UserNotFoundException::new);
        return new UserOutput(
                user.getName(),
                user.getDocument(),
                user.getEmail()
        );
    }
}
