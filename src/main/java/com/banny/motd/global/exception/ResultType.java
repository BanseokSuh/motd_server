package com.banny.motd.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultType {

    SUCCESS("0000", "success"),

    FAIL_INVALID_TOKEN("1001", "invalid token"),

    FAIL_USER_NOT_FOUND("2001", "user not found"),
    FAIL_USER_PASSWORD_MISMATCH("2002", "user password mismatch"),
    FAIL_USER_DUPLICATED("2003", "user duplicated"),

    FAIL_POST_NOT_FOUND("3001", "post not found"),
    FAIL_INVALID_PERMISSION("3002", "invalid permission"),

    FAIL_ALARM_CONNECT_ERROR("5001", "alarm connect error"),

    FAIL_ALREADY_LOGGED_IN("6001", "already logged in"),

    FAIL_INVALID_PARAMETER("9997", "invalid parameter"),
    FAIL_VALIDATE_ERROR("9998", "validate error"),
    FAIL_SERVER_ERROR("9999", "server error");


    private final String code;
    private final String desc;

}
