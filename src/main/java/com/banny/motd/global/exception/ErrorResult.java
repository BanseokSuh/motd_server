package com.banny.motd.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResult {

    private String message;

    public static ErrorResult error() {
        return new ErrorResult(null);
    }

    public static ErrorResult error(String message) {
        return new ErrorResult(message);
    }
}
