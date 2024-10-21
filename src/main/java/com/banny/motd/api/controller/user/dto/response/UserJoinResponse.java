package com.banny.motd.api.controller.user.dto.response;

import com.banny.motd.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter // Jackson이 serialize하기 위해 getter를 통해 필드에 접근하기 때문에 get 메서드를 사용하지 않더라도 추가해야 함
@AllArgsConstructor // from() 메서드에서 사용하기 위해 추가
public class UserJoinResponse {

    private Long id;
    private String loginId;
    private String userName;
    private String nickName;

    // 도메인 객체와 dto 객체 간의 변환을 위한 정적 메서드
    public static UserJoinResponse from(User user) {
        return new UserJoinResponse(
                user.getId(),
                user.getLoginId(),
                user.getUsername(),
                user.getNickName()
        );
    }

}
