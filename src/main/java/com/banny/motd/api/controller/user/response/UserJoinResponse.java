package com.banny.motd.api.controller.user.response;

import com.banny.motd.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter // Jackson이 serialize하기 위해 getter를 통해 필드에 접근하기 때문에 get 메서드를 사용하지 않더라도 추가해야 함
public class UserJoinResponse {

    private Long id;
    private String loginId;
    private String userName;
    private String nickName;

    @Builder
    public UserJoinResponse(Long id, String loginId, String userName, String nickName) {
        this.id = id;
        this.loginId = loginId;
        this.userName = userName;
        this.nickName = nickName;
    }

    public static UserJoinResponse from(User user) {
        return UserJoinResponse.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .userName(user.getUsername())
                .nickName(user.getNickName())
                .build();
    }

}
