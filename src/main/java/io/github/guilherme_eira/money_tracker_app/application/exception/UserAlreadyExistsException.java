package io.github.guilherme_eira.money_tracker_app.application.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("Os dados informados já estão em uso por outra conta.");
    }
}
