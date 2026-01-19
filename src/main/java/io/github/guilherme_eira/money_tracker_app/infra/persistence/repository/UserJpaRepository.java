package io.github.guilherme_eira.money_tracker_app.infra.persistence.repository;

import io.github.guilherme_eira.money_tracker_app.infra.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    @Query("SELECT u FROM UserEntity u WHERE u.email = :identifier OR u.document = :identifier")
    Optional<UserEntity> findByEmailOrDocument(String identifier);
    Optional<UserEntity> findByPasswordResetToken(UUID token);
    Boolean existsByDocumentOrEmail(String document, String email);
}
