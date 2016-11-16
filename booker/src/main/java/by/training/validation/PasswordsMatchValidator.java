package by.training.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import by.training.dto.RegisterDTO;
import by.training.validation.constraints.PasswordsMatch;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, RegisterDTO> {

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegisterDTO register, ConstraintValidatorContext content) {
        return (register.getPassword() == null) || (register.getConfirmPassword() == null)
                || (register.getPassword().equals(register.getConfirmPassword()));
    }

}
