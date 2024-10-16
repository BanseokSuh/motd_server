package com.banny.motd.global.exception;

import com.banny.motd.global.dto.response.StstusObject;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponseEntity<T> {

    private StstusObject status;
    private T result;

    /**
     * 모든 예외 정보를 ResponseEntity에 담아 반환
     * @param status
     * @param errorResult
     * @return
     * @param <T>
     */
    public static <T> ResponseEntity<ErrorResponseEntity<T>> toResponseEntity(StstusObject status, T errorResult) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseEntity.<T>builder()
                        .status(status)
                        .result(errorResult)
                        .build());
    }

}
