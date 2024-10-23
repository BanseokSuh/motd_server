package com.banny.motd.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResult {

    private String message;

    public static ErrorResult of(String message) {
        return new ErrorResult(message);
    }

    public static ErrorResult of() {
        return new ErrorResult(null);
    }

}
