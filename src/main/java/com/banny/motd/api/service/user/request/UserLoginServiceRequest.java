package com.banny.motd.api.service.user.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginServiceRequest {

    private String loginId;
    private String password;
    private String device;

    @Builder
    public UserLoginServiceRequest(String loginId, String password, String device) {
        this.loginId = loginId;
        this.password = password;
        this.device = device;
    }

}