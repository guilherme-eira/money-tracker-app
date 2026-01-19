package io.github.guilherme_eira.money_tracker_app.application.exception;

public class GoalAlreadyExistsException extends RuntimeException {
    public GoalAlreadyExistsException() {
        super("Já existe uma meta para esta categoria este mês.");
    }
}
