package com.banny.motd.api.controller.user.response;

import com.banny.motd.domain.user.Tokens;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginResponse {

    private String accessToken;
    private String refreshToken;

    @Builder
    private UserLoginResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static UserLoginResponse from(Tokens token) {
        return UserLoginResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }

}
