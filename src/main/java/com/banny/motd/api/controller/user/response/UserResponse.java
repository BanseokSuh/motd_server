package com.banny.motd.api.controller.user.response;

import com.banny.motd.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    private Long id;
    private String loginId;
    private String userName;

    @Builder
    private UserResponse(Long id, String loginId, String userName) {
        this.id = id;
        this.loginId = loginId;
        this.userName = userName;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .userName(user.getUsername())
                .build();
    }

}
