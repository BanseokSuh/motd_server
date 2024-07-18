package com.banny.motd.global.exception;

import com.banny.motd.global.response.ResultObject;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
@Data
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

//    private ResultObject result;
//    private ErrorResult data;
//
//    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ResultObject result, ErrorResult errorResult) {
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(ErrorResponseEntity.builder()
//                        .result(result)
//                        .data(errorResult)
//                        .build());
//    }
}
