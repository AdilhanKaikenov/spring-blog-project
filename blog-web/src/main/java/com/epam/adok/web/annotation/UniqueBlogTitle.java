package com.epam.adok.web.annotation;

import com.epam.adok.web.validator.UniqueBlogTitleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {UniqueBlogTitleValidator.class})
public @interface UniqueBlogTitle {

    String message() default "Blog with such title already exist";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
