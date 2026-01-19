package io.github.guilherme_eira.money_tracker_app.infra.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Model model, Exception ex) {
        System.err.println("ERRO: " + ex.getMessage());
        model.addAttribute("message", ex.getMessage());
        return "/error";
    }
}
