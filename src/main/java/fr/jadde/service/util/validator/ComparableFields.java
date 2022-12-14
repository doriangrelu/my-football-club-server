package fr.jadde.service.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ComparableFieldValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComparableFields {

    String first();

    String second();

    ComparableType type();

    String message() default "{error.comparable}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
