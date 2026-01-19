package io.github.guilherme_eira.money_tracker_app.application.gateway;

public interface PasswordEncoderGateway {
    String encode(String rawPassword);
    Boolean matches(String rawPassword, String hashedPassword);
}
