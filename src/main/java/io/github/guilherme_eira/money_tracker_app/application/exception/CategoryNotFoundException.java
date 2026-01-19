package io.github.guilherme_eira.money_tracker_app.application.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super("Não há nenhuma categoria correspondente.");
    }
}
