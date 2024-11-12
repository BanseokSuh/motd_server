package com.banny.motd.global.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiExceptionResult {

    private String message;

    @Builder
    private ApiExceptionResult(String message) {
        this.message = message;
    }

    public static ApiExceptionResult of(String message) {
        return ApiExceptionResult.builder().message(message).build();
    }

    public static ApiExceptionResult of() {
        return ApiExceptionResult.builder().build();
    }

}
