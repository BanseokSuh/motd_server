package com.lightcc.motd.domain.user.api.dto.request;

import lombok.Getter;

@Getter
public class UserLoginRequest {

    private String loginId;
    private String password;
}
