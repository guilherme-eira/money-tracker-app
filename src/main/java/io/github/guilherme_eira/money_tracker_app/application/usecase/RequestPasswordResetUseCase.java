package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.gateway.EmailSenderGateway;
import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestPasswordResetUseCase {

    private final UserRepository repository;
    private final EmailSenderGateway emailSender;

    @Transactional
    @Async
    public void execute(String email) {
        var user = repository.findByEmail(email);

        if (user.isPresent()) {
            var token = UUID.randomUUID();
            user.get().setPasswordResetToken(token);
            user.get().setTokenExpiration(LocalDateTime.now().plusMinutes(15));

            try {
                emailSender.sendAccountRecoverEmail(email, token.toString());
            } catch (MessagingException ex){
                System.err.println("Falha ao enviar token para " + email);
            }

            repository.save(user.get());
        }
    }
}
