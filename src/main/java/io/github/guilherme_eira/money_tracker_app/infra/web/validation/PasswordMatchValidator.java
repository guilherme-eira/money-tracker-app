package io.github.guilherme_eira.money_tracker_app.infra.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String passwordField;
    private String confirmPasswordField;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.passwordField = constraintAnnotation.passwordField();
        this.confirmPasswordField = constraintAnnotation.confirmPasswordField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            var beanWrapper = new BeanWrapperImpl(value);
            Object passwordValue = beanWrapper.getPropertyValue(passwordField);
            Object confirmPasswordValue = beanWrapper.getPropertyValue(confirmPasswordField);

            boolean isValid = false;

            if (passwordValue != null) {
                isValid = passwordValue.equals(confirmPasswordValue);
            } else {
                isValid = confirmPasswordValue == null;
            }

            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode(confirmPasswordField)
                        .addConstraintViolation();
            }

            return isValid;

        } catch (Exception e) {
            return false;
        }
    }
}