package com.banny.motd.domain.user.api.dto.request;


import lombok.Getter;

@Getter
public class UserJoinRequest {
    
    private String loginId;
    private String userName;
    private String password;
    private String gender;
}
