package com.lightcc.motd.domain.user.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {

    private String loginId;
    private String password;
}
