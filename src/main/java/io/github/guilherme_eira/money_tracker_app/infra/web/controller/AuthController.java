package io.github.guilherme_eira.money_tracker_app.infra.web.controller;

import io.github.guilherme_eira.money_tracker_app.application.dto.auth.ResetPasswordInput;
import io.github.guilherme_eira.money_tracker_app.application.usecase.RequestPasswordResetUseCase;
import io.github.guilherme_eira.money_tracker_app.application.usecase.ResetPasswordUseCase;
import io.github.guilherme_eira.money_tracker_app.infra.web.form.AccountRecoverForm;
import io.github.guilherme_eira.money_tracker_app.infra.web.form.ResetPasswordForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RequestPasswordResetUseCase requestPasswordResetUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    private final String RESET_PASSWORD_TEMPLATE = "auth/reset-password";

    @GetMapping("/login")
    public String showLoginForm(){
        return "auth/login";
    }

    @GetMapping("/account-recover")
    public String showForgotPasswordForm(Model model){
        model.addAttribute("accountRecoverForm", new AccountRecoverForm(null));
        return "auth/account-recover";
    }

    @PostMapping("/account-recover")
    public String showForgotPasswordForm(@Valid @ModelAttribute("accountRecoverForm") AccountRecoverForm form, RedirectAttributes redirectAttributes){
        requestPasswordResetUseCase.execute(form.email());
        redirectAttributes.addFlashAttribute("successMessage", "Solicitação recebida. Se o e-mail estiver cadastrado, enviamos as instruções.");
        return "redirect:/auth/login";
    }

    @GetMapping("/reset-password")
    public String showNewPasswordForm(@RequestParam String token, Model model){
        model.addAttribute("resetPasswordForm", new ResetPasswordForm(token, null, null));
        return RESET_PASSWORD_TEMPLATE;
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute("resetPasswordForm") ResetPasswordForm form, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if (result.hasErrors()){
            return RESET_PASSWORD_TEMPLATE;
        }
        try {
            resetPasswordUseCase.execute(new ResetPasswordInput(form.token(), form.newPassword()));
            redirectAttributes.addFlashAttribute("successMessage", "Sua senha foi alterada com sucesso! Faça login usando suas novas credenciais.");
        } catch (Exception ex){
            model.addAttribute("errorMessage", ex.getMessage());
            return RESET_PASSWORD_TEMPLATE;
        }
        return "redirect:/auth/login";
    }
}
