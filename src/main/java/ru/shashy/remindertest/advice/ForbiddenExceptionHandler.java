package ru.shashy.remindertest.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.shashy.remindertest.dto.error.AppError;
import ru.shashy.remindertest.exception.ForbiddenException;

@RestControllerAdvice
public class ForbiddenExceptionHandler {
    @ExceptionHandler(value = ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AppError handleForbiddenException(ForbiddenException forbiddenException){
        return new AppError(HttpStatus.FORBIDDEN.value(), forbiddenException.getMessage());
    }
}
