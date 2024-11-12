package com.banny.motd.api.controller.user.response;

import com.banny.motd.domain.user.Gender;
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
    private Gender gender;
    private UserRole userRole;
    private UserStatus userStatus;

    @Builder
    private UserMyResponse(Long id, String loginId, String userName, String email, Gender gender, UserRole userRole, UserStatus userStatus) {
        this.id = id;
        this.loginId = loginId;
        this.userName = userName;
        this.email = email;
        this.gender = gender;
        this.userRole = userRole;
        this.userStatus = userStatus;
    }

    public static UserMyResponse from(User user) {
        return UserMyResponse.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .gender(user.getGender())
                .userRole(user.getUserRole())
                .userStatus(user.getUserStatus())
                .build();
    }

}
