package io.github.guilherme_eira.money_tracker_app.application.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
        super("Não há nenhuma transação correspondente.");
    }
}
