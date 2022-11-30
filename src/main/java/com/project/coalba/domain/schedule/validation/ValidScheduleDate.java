package com.project.coalba.domain.schedule.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidScheduleDate.ScheduleDateValidator.class)
public @interface ValidScheduleDate {

    String message() default "현재로부터 +1 ~ +90일 날짜까지만 스케줄 생성이 가능합니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    class ScheduleDateValidator implements ConstraintValidator<ValidScheduleDate, LocalDateTime> {

        private static final int SCHEDULE_POSSIBLE_DAY_PERIOD = 90;

        @Override
        public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
            LocalDate scheduleDate = value.toLocalDate(), currentDate = LocalDate.now();
            LocalDate schedulePossibleMaxDate = currentDate.plusDays(SCHEDULE_POSSIBLE_DAY_PERIOD);
            return scheduleDate.isAfter(currentDate) &&
                    scheduleDate.isBefore(schedulePossibleMaxDate) || scheduleDate.isEqual(schedulePossibleMaxDate);
        }
    }
}
