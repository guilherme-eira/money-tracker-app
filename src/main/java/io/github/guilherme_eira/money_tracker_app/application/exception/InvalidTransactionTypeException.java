package io.github.guilherme_eira.money_tracker_app.application.exception;

public class InvalidTransactionTypeException extends RuntimeException {
    public InvalidTransactionTypeException() {
        super("Tipo de transação inválida.");
    }
}
