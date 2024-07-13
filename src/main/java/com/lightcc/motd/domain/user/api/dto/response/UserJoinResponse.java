package com.lightcc.motd.domain.user.api.dto.response;

import com.lightcc.motd.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinResponse {

    private Long id;
    private String loginId;
    private String userName;

    public static UserJoinResponse from(User user) {
        return new UserJoinResponse(
                user.getId(),
                user.getLoginId(),
                user.getUsername()
        );
    }
}
