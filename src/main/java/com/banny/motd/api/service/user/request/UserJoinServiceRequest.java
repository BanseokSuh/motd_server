package com.banny.motd.api.service.user.request;


import com.banny.motd.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserJoinServiceRequest {

    private String loginId;
    private String userName;
    private String nickName;
    private String password;
    private String email;
    private String gender;

    @Builder
    public UserJoinServiceRequest(String loginId, String userName, String nickName, String password, String email, String gender) {
        this.loginId = loginId;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.gender = gender;
    }
    
}
