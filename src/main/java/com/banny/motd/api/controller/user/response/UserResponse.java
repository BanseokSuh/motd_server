package com.banny.motd.api.controller.user.response;

import com.banny.motd.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String loginId;
    private String userName;

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getLoginId(), user.getUsername());
    }

}
