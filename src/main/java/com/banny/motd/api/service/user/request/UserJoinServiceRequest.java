package com.banny.motd.api.service.user.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserJoinServiceRequest {

    private String loginId;
    private String userName;
    private String nickName;
    private String email;
    private String password;

    @Builder
    public UserJoinServiceRequest(String loginId, String userName, String nickName, String password, String email) {
        this.loginId = loginId;
        this.userName = userName;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
    }

}
