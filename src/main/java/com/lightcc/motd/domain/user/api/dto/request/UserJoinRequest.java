package com.lightcc.motd.domain.user.api.dto.request;

import lombok.Getter;

@Getter
public class UserJoinRequest {

    private String loginId;
    private String userName;
    private String password;
}
