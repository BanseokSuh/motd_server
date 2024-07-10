package com.lightcc.motd.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultType {
    SUCCESS("0000", "success"),
    SERVER_ERROR("9999", "server error")
    ;

    private final String code;
    private final String desc;
}
