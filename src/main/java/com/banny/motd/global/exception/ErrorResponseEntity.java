package com.banny.motd.global.exception;

import com.banny.motd.global.dto.response.ResultObject;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponseEntity<T> {

    private ResultObject result;
    private T data;

    public static <T> ResponseEntity<ErrorResponseEntity<T>> toResponseEntity(ResultObject result, T errorResult) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseEntity.<T>builder()
                        .result(result)
                        .data(errorResult)
                        .build());
    }
}
