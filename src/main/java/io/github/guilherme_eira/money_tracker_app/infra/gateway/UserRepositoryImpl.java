package io.github.guilherme_eira.money_tracker_app.infra.gateway;

import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
import io.github.guilherme_eira.money_tracker_app.domain.model.User;
import io.github.guilherme_eira.money_tracker_app.infra.mapper.UserMapper;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;
    private final UserMapper mapper;

    @Override
    public void save(User user) {
        var userEntity = mapper.toEntity(user);
        repository.save(userEntity);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        var user = repository.findById(userId);
        return user.map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        var user = repository.findByEmailOrDocument(email);
        return user.map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByPasswordResetToken(UUID token) {
        var user = repository.findByPasswordResetToken(token);
        return user.map(mapper::toDomain);
    }

    @Override
    public Boolean existsByDocumentOrEmail(String document, String email) {
        return repository.existsByDocumentOrEmail(document, email);
    }

    @Override
    public void delete(User user) {
        repository.delete(mapper.toEntity(user));
    }

}


