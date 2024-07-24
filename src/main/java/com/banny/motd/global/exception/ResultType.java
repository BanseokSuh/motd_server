package com.banny.motd.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultType {
    SUCCESS("0000", "success"),

    INVALID_TOKEN("1001", "invalid token"),

    USER_NOT_FOUND("2001", "user not found"),
    USER_PASSWORD_MISMATCH("2002", "user password mismatch"),
    USER_DUPLICATED("2003", "user duplicated"),

    INVALID_LOGIN_ID("2004", "invalid login id"),
    INVALID_PASSWORD("2005", "invalid password"),

    POST_NOT_FOUND("3001", "post not found"),
    INVALID_PERMISSION("3002", "invalid permission"),

    ALREADY_LIKED("4001", "already liked"),

    ALARM_CONNECT_ERROR("5001", "alarm connect error"),

    VALIDATE_ERROR("9998", "validate error"),
    SERVER_ERROR("9999", "server error");


    private final String code;
    private final String desc;
}
