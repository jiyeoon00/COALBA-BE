package com.project.coalba.domain.schedule.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.LocalDateTime;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidTimeMinUnit.TimeMinUnitValidator.class)
public @interface ValidTimeMinUnit {
    String message() default "스케줄 시간은 10분 단위이어야 합니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    class TimeMinUnitValidator implements ConstraintValidator<ValidTimeMinUnit, LocalDateTime> {

        private static final int TIME_MIN_UNIT = 10;

        @Override
        public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
            return value.getMinute() % TIME_MIN_UNIT == 0;
        }
    }
}
