package io.github.guilherme_eira.money_tracker_app.application.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Token inválido ou não encontrado.");
    }
}
