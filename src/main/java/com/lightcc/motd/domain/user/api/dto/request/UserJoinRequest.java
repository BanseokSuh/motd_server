package com.lightcc.motd.domain.user.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinRequest {

    private String loginId;
    private String userName;
    private String password;
}
