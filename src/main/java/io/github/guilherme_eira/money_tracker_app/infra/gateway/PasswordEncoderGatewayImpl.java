package io.github.guilherme_eira.money_tracker_app.infra.gateway;

import io.github.guilherme_eira.money_tracker_app.application.gateway.PasswordEncoderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncoderGatewayImpl implements PasswordEncoderGateway {

    private final PasswordEncoder encoder;

    @Override
    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public Boolean matches(String rawPassword, String hashedPassword){
        return encoder.matches(rawPassword, hashedPassword);
    }
}
