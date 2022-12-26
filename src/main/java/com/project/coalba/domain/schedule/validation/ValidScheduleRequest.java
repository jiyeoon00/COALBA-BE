package com.project.coalba.domain.schedule.validation;

import com.project.coalba.domain.schedule.dto.request.ScheduleCreateRequest;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.LocalDateTime;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidScheduleRequest.ScheduleRequestValidator.class)
public @interface ValidScheduleRequest {

    String message() default "스케줄 시작 날짜 시간은 스케줄 종료 날짜 시간보다 앞서야 합니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    class ScheduleRequestValidator implements ConstraintValidator<ValidScheduleRequest, ScheduleCreateRequest> {

        @Override
        public boolean isValid(ScheduleCreateRequest value, ConstraintValidatorContext context) {
            LocalDateTime scheduleStartDateTime = value.getScheduleStartDateTime();
            LocalDateTime scheduleEndDateTime = value.getScheduleEndDateTime();
            return scheduleStartDateTime.isBefore(scheduleEndDateTime);
        }
    }
}
