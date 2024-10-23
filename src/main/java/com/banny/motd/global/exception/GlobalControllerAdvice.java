package com.banny.motd.global.exception;

import com.banny.motd.global.dto.response.ApiResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    /**
     * 애플리케이션에서 정의된 예외 핸들링
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> applicationExceptionHandler(ApplicationException e) {
        return ErrorResponseEntity.toResponseEntity(e.getStatus(), e.getResult());
    }

    /**
     * 애플리케이션에서 정의되지 않은 런테입 예외 핸들링
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> internalServerExceptionHandler(RuntimeException e) {
        return ErrorResponseEntity.toServerExceptionResponseEntity(
                ApiResponseStatus.error(), ErrorResult.error()
        );
    }

    /**
     * Dto에서 검증되지 못한 Validation 예외 핸들링
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((ObjectError error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ErrorResponseEntity.toResponseEntity(
                ApiResponseStatus.validateError(), errors
        );
    }

}
