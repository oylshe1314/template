package com.sk.op.application.common.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class InValidator implements ConstraintValidator<In, Integer> {

    private boolean notNull;

    private String message;

    private Set<Integer> values;

    @Override
    public void initialize(In in) {
        this.values = new LinkedHashSet<>();
        for (int value : in.values()) {
            values.add(value);
        }
        this.notNull = in.notNull();
        this.message = in.message();
        if (!StringUtils.hasText(this.message) && this.values.size() > 0) {
            this.message = "允许值：" + this.values.stream().map(String::valueOf).collect(Collectors.joining(", ", "{", "}"));
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (!this.notNull && value == null) {
            return true;
        }

        if (this.values.contains(value)) {
            return true;
        }

        if (context.getDefaultConstraintMessageTemplate().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.message).addConstraintViolation();
        }
        return false;
    }
}
