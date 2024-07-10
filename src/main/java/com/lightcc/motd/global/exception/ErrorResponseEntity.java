package com.lightcc.motd.global.exception;

import com.lightcc.motd.global.response.ResultObject;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
@Data
public class ErrorResponseEntity {

    private ResultObject result;
    private ErrorResult data;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(String message) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseEntity.builder()
                        .result(ResultObject.error())
                        .data(ErrorResult.builder()
                                .message(message)
                                .build())
                        .build());
    }


}
