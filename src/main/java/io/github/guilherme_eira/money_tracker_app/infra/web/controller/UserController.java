package io.github.guilherme_eira.money_tracker_app.infra.web.controller;

import io.github.guilherme_eira.money_tracker_app.application.dto.user.ChangePasswordInput;
import io.github.guilherme_eira.money_tracker_app.application.dto.user.CreateUserInput;
import io.github.guilherme_eira.money_tracker_app.application.dto.user.UpdateUserInput;
import io.github.guilherme_eira.money_tracker_app.application.query.FindUserForUpdateQuery;
import io.github.guilherme_eira.money_tracker_app.application.usecase.ChangePasswordUseCase;
import io.github.guilherme_eira.money_tracker_app.application.usecase.CreateUserUseCase;
import io.github.guilherme_eira.money_tracker_app.application.usecase.DeleteUserUseCase;
import io.github.guilherme_eira.money_tracker_app.application.usecase.UpdateUserUseCase;
import io.github.guilherme_eira.money_tracker_app.infra.security.SecurityUser;
import io.github.guilherme_eira.money_tracker_app.infra.web.form.ChangePasswordForm;
import io.github.guilherme_eira.money_tracker_app.infra.web.form.CreateUserForm;
import io.github.guilherme_eira.money_tracker_app.infra.web.form.UpdateUserForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final FindUserForUpdateQuery findUserForUpdateQuery;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    private final String CREATION_FORM_TEMPLATE = "app/user/create";
    private final String UPDATE_FORM_TEMPLATE = "app/user/profile";
    private final String CHANGE_PASSWORD_FORM_TEMPLATE = "app/user/password";

    @GetMapping("/create")
    public String showCreationForm(Model model){
        model.addAttribute("createUserForm", new CreateUserForm(null, null, null, null, null));
        return CREATION_FORM_TEMPLATE;
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("createUserForm") CreateUserForm form, BindingResult result, RedirectAttributes redirectAttributes , Model model){
        if (result.hasErrors()){
            return CREATION_FORM_TEMPLATE;
        }
        try {
            createUserUseCase.execute(new CreateUserInput(form.name(), form.document(), form.email(), form.password()));
            redirectAttributes.addFlashAttribute("successMessage", "Cadastro realizado com sucesso! Faça login para continuar.");

        } catch(Exception ex){
            model.addAttribute("errorMessage", ex.getMessage());
            return CREATION_FORM_TEMPLATE;
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/update")
    public String showUpdateForm(@AuthenticationPrincipal SecurityUser securityUser, Model model){
        var user = findUserForUpdateQuery.execute(securityUser.getId());
        model.addAttribute("updateUserForm", new UpdateUserForm(user.name(), user.document(), user.email()));
        return UPDATE_FORM_TEMPLATE;
    }

    @PostMapping("/update")
    public String updateUser(@AuthenticationPrincipal SecurityUser securityUser, @Valid @ModelAttribute("updateUserForm") UpdateUserForm form, BindingResult result, RedirectAttributes redirectAttributes, Model model){

        var user = findUserForUpdateQuery.execute(securityUser.getId());

        if (result.hasErrors()){
            form.setDocument(user.document());
            form.setEmail(user.email());
            return UPDATE_FORM_TEMPLATE;
        }
        try {
            updateUserUseCase.execute(new UpdateUserInput(securityUser.getId(), form.getName()));
            securityUser.setName(form.getName());
            reloadUserSession(securityUser);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário atualizado com sucesso!");
        } catch(Exception ex){
            form.setDocument(user.document());
            form.setEmail(user.email());
            model.addAttribute("errorMessage", ex.getMessage());
            return UPDATE_FORM_TEMPLATE;
        }
        return "redirect:/overview";
    }

    @PostMapping("/delete")
    public String deleteUser(@AuthenticationPrincipal SecurityUser user, HttpServletRequest request, HttpServletResponse response){
        deleteUserUseCase.execute(user.getId());
        forceLogout(request, response);
        return "redirect:/auth/login?deleted=true";
    }

    @GetMapping("/change-password")
    public String showPasswordChangeForm(Model model){
        model.addAttribute("changePasswordForm", new ChangePasswordForm(null, null, null));
        return CHANGE_PASSWORD_FORM_TEMPLATE;
    }

    @PostMapping("/change-password")
    public String changePassword(Model model,
                                 @Valid @ModelAttribute("changePasswordForm") ChangePasswordForm form,
                                 BindingResult result,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 @AuthenticationPrincipal SecurityUser user){
        if (result.hasErrors()){
            return CHANGE_PASSWORD_FORM_TEMPLATE;
        }
        try {
            changePasswordUseCase.execute(new ChangePasswordInput(user.getId(), form.currentPassword(), form.newPassword()));
            forceLogout(request, response);

        } catch(Exception ex){
            model.addAttribute("errorMessage", ex.getMessage());
            return CHANGE_PASSWORD_FORM_TEMPLATE;

        }
        return "redirect:/auth/login?password-changed=true";
    }

    private void reloadUserSession(SecurityUser updatedUser) {
        var currentAuth = SecurityContextHolder.getContext().getAuthentication();
        var newAuth = new UsernamePasswordAuthenticationToken(
                updatedUser,
                currentAuth.getCredentials(),
                updatedUser.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    private void forceLogout(HttpServletRequest request, HttpServletResponse response) {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            new CookieClearingLogoutHandler("JSESSIONID", "remember-me").logout(request, response, auth);
        }
    }

}
