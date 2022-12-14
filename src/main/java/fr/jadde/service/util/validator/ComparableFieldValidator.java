package fr.jadde.service.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

public class ComparableFieldValidator implements ConstraintValidator<ComparableFields, Object> {

    private String firstField;

    private String secondField;

    private ComparableType type;

    private static String firstToUpperCase(final String target) {
        return null != target && target.length() > 0 ? target.substring(0, 1).toUpperCase() + target.substring(1) : target;
    }

    @Override
    public void initialize(final ComparableFields constraintAnnotation) {
        this.firstField = constraintAnnotation.first();
        this.secondField = constraintAnnotation.second();
        this.type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(final Object o, final ConstraintValidatorContext constraintValidatorContext) {
        final Comparable<Object> firstValue = this.extractValue(o, this.firstField);
        final Comparable<Object> secondValue = this.extractValue(o, this.secondField);
        if (null == firstValue && null == secondValue) {
            return true;
        } else if (null == firstValue ^ null == secondValue) {
            return false;
        }
        return Objects.compare(firstValue, secondValue, Comparator.naturalOrder()) == this.type.getValueStatus();
    }

    @SuppressWarnings("unchecked")
    private Comparable<Object> extractValue(final Object target, final String propertyName) {
        return Stream.of("get" + firstToUpperCase(propertyName), propertyName)
                .flatMap(name -> Arrays.stream(target.getClass().getMethods()).filter(method -> method.getName().equals(name)))
                .filter(method -> method.getParameterCount() == 0)
                .filter(method -> !Void.class.isAssignableFrom(method.getReturnType()))
                .map(method -> {
                    try {
                        return method.invoke(target);
                    } catch (final IllegalAccessException | InvocationTargetException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .findFirst()
                .filter(o -> o instanceof Comparable<?>)
                .map(Comparable.class::cast)
                .orElse(null);
    }

}
