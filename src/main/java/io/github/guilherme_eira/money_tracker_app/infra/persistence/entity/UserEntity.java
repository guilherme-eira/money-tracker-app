package io.github.guilherme_eira.money_tracker_app.infra.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    private UUID id;
    private String name;
    private String document;
    private String email;
    private String password;
    private UUID passwordResetToken;
    private LocalDateTime tokenExpiration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
