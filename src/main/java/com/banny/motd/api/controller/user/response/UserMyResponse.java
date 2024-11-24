package com.banny.motd.api.controller.user.response;

import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.UserRole;
import com.banny.motd.domain.user.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserMyResponse {

    private Long id;
    private String loginId;
    private String userName;
    private String email;
    private UserRole userRole;
    private UserStatus userStatus;

    @Builder
    private UserMyResponse(Long id, String loginId, String userName, String email, UserRole userRole, UserStatus userStatus) {
        this.id = id;
        this.loginId = loginId;
        this.userName = userName;
        this.email = email;
        this.userRole = userRole;
        this.userStatus = userStatus;
    }

    public static UserMyResponse from(User user) {
        return UserMyResponse.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .userStatus(user.getUserStatus())
                .build();
    }

}
