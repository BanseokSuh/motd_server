package com.banny.motd.global.exception;

import com.banny.motd.global.dto.response.ResultObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    /**
     * 정의된 에러 핸들링
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> applicationExceptionHandler(ApplicationException e) {
        log.error("""
                        ########### Application exception occurred ###########
                        ####### code: {}
                        ####### desc: {}
                        ####### message: {}""",
                e.getResult().getCode(),
                e.getResult().getDesc(),
                e.getData().getMessage());

        return ErrorResponseEntity.toResponseEntity(e.getResult(), e.getData());
    }

    /**
     * 미정의된 에러 핸들링
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> globalExceptionHandler(RuntimeException e) {
        log.error("""
                        ########### Runtime exception occurred ###########
                        ####### error: {}""",
                e.toString());

        return ErrorResponseEntity.toResponseEntity(ResultObject.error(), ErrorResult.error());
    }

    /**
     * Dto Validation 에러 핸들링
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        /*
         * validation에 걸린 모든 필드와 메시지를 map에 담는다.
         * {
         *   "field1": "message1",
         *   "field2": "message2",
         *   ...
         * }
         */
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ErrorResponseEntity.toResponseEntity(ResultObject.validateError(), errors);
    }
}
