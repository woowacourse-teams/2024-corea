package corea.global.annotation;

import corea.exception.ExceptionType;
import corea.exception.ExceptionTypeGroup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorResponses {

    ExceptionType[] value() default {};

    ExceptionTypeGroup[] groups() default {};
}
