package io.github.guilherme_eira.money_tracker_app.application.exception;

public class PasswordDoesNotMatchActualException extends RuntimeException {
    public PasswordDoesNotMatchActualException() {
        super("A senha informada não confere com a sua senha atual.");
    }
}
