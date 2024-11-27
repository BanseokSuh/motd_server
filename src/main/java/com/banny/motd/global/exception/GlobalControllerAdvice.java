package com.banny.motd.global.exception;

import com.banny.motd.global.dto.response.ApiResponse;
import com.banny.motd.global.dto.response.ApiStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    /**
     * 애플리케이션에서 정의된 예외 핸들링
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ApplicationException.class)
    public ApiResponse<Object> applicationExceptionHandler(ApplicationException e) {
        return ApiResponse.of(e.getStatus(), e.getResult());
    }

    /**
     * 애플리케이션에서 정의되지 않은 런테입 예외 핸들링
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<Object> internalServerExceptionHandler(RuntimeException e) {
        log.error(e.toString());
        return ApiResponse.of(ApiStatus.serverError(), ApiExceptionResult.of(e.getMessage()));
    }

    /**
     * Dto에서 검증되지 못한 Validation 예외 핸들링
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> validationExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((ObjectError error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ApiResponse.of(ApiStatus.validationError(), errors);
    }

}
