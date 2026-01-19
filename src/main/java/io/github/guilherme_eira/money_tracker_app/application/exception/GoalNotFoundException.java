package io.github.guilherme_eira.money_tracker_app.application.exception;

public class GoalNotFoundException extends RuntimeException {
    public GoalNotFoundException() {
        super("Não há nenhuma meta correspondente.");
    }
}
