package io.github.guilherme_eira.money_tracker_app.application.exception;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException() {
        super("Token expirado.");
    }
}
