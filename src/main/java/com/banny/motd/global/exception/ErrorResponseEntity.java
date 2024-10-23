package com.banny.motd.global.exception;

import com.banny.motd.global.dto.response.ApiResponseStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponseEntity<T> {

    private ApiResponseStatus status;
    private T result;

    public static <T> ResponseEntity<ErrorResponseEntity<T>> toServerExceptionResponseEntity(ApiResponseStatus status, T errorResult) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseEntity.<T>builder()
                        .status(status)
                        .result(errorResult)
                        .build());
    }

    public static <T> ResponseEntity<ErrorResponseEntity<T>> toResponseEntity(ApiResponseStatus status, T errorResult) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseEntity.<T>builder()
                        .status(status)
                        .result(errorResult)
                        .build());
    }

}
