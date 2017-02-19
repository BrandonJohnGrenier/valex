package fm.pattern.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.Validator;

import fm.pattern.validation.sequences.Create;
import fm.pattern.validation.sequences.Delete;
import fm.pattern.validation.sequences.Update;

public class SimpleValidationService implements ValidationService {

    private final Validator validator;

    public SimpleValidationService(Validator validator) {
        this.validator = validator;
    }

    public <T> Result<T> validate(T instance, Class<?>... types) {
        ValidationResult result = ErrorConverter.convert(validator.validate(instance, types));
        return result.containsErrors() ? Result.unprocessable_entity(instance, result.getErrors()) : accept(instance, types);
    }

    private <T> Result<T> accept(T instance, Class<?>... types) {
        List<Class<?>> list = Arrays.asList(types);

        if (list.contains(Create.class)) {
            return Result.created(instance);
        }
        if (list.contains(Update.class)) {
            return Result.updated(instance);
        }
        if (list.contains(Delete.class)) {
            return Result.deleted(instance);
        }

        return Result.accept(instance);
    }

}