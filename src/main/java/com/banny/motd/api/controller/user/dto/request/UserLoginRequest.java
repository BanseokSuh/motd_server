package com.banny.motd.api.controller.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserLoginRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 4, max = 20, message = "아이디는 4~20자 사이로 입력해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 16, message = "비밀번호는 8~16자 사이로 입력해주세요.")
    private String password;

    @NotBlank(message = "디바이스를 입력해주세요.")
    private String device;

}