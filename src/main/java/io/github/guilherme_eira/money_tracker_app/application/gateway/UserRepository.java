package io.github.guilherme_eira.money_tracker_app.application.gateway;

import io.github.guilherme_eira.money_tracker_app.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(UUID userId);
    Optional<User> findByEmail(String email);
    Optional<User> findByPasswordResetToken(UUID token);
    Boolean existsByDocumentOrEmail(String document, String email);
    void delete(User user);
}
