package io.github.guilherme_eira.money_tracker_app.application.gateway;

import jakarta.mail.MessagingException;

public interface EmailSenderGateway {
    void sendAccountRecoverEmail(String email, String token) throws MessagingException;
}
