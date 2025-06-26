package it.uniroma3.authentication;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface PasswordMatch {
    String message() default "Le password non coincidono";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
