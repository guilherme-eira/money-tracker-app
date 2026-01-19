package io.github.guilherme_eira.money_tracker_app.infra.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatch {

    String message() default "As senhas não conferem";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String passwordField();
    String confirmPasswordField();
}