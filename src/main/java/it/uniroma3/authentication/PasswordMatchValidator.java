package it.uniroma3.authentication;

import it.uniroma3.model.Utente;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Utente> {
        @Override
    public boolean isValid(Utente u, ConstraintValidatorContext ctx) {
        if (u.getPassword() == null || u.getPasswordBis() == null) {
            return true; // lascia @NotBlank/dd'altre validazioni gestire null
        }
        boolean match = u.getPassword().equals(u.getPasswordBis());
        if (!match) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
               .addPropertyNode("passwordBis")
               .addConstraintViolation();
        }
        return match;
    }
}
