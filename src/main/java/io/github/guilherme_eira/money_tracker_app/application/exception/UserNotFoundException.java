package io.github.guilherme_eira.money_tracker_app.application.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Não há nenhum usuário correspondente.");
    }
}
