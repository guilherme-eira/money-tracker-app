package io.github.guilherme_eira.money_tracker_app.application.exception;

public class InvalidStartDateException extends RuntimeException {
    public InvalidStartDateException() {
        super("O mês de início da meta deve ser atual ou futuro.");
    }
}
