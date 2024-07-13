package com.lightcc.motd.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorResult {

    private String message;

    public static ErrorResult error() {
        return new ErrorResult(null);
    }
}
