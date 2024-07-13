package com.lightcc.motd.domain.user.api.dto.response;

import com.lightcc.motd.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String loginId;
    private String userName;
    private String email;

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getLoginId(), user.getUsername(), user.getEmail());
    }
}
