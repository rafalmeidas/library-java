package br.objective.training.library.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TestControllerAdvice {
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(StringUtils.parseLocaleString("pt_BR"));
        return localeResolver;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorsDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ValidationErrorsDto(exception);
    }

    public final static class ValidationErrorsDto implements Serializable {
        @Serial
        private static final long serialVersionUID = -578954576020911385L;

        private final List<FieldError> errors;

        ValidationErrorsDto(MethodArgumentNotValidException exception) {
            errors = exception.getBindingResult().getFieldErrors();
        }

        public List<ValidationFieldError> getErrors() {
            return errors.stream()
                    .map(ValidationFieldError::new)
                    .collect(Collectors.toList());
        }

        private final static class ValidationFieldError {
            private final String fieldName;
            private final String message;

            ValidationFieldError(FieldError fieldError) {
                fieldName = fieldError.getField();
                message = fieldError.getDefaultMessage();
            }

            public String getFieldName() {
                return fieldName;
            }

            public String getMessage() {
                return message;
            }
        }
    }
}
