package com.banny.motd.domain.user.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {

    private String token;

    public static UserLoginResponse from(String token) {
        return new UserLoginResponse(token);
    }
}
