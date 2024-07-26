package com.banny.motd.domain.user.api.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserJoinRequest {

    @NotBlank(message = "아이디를 입력해주세요")
    private String loginId;

    @NotBlank(message = "이름을 입력해주세요")
    private String userName;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "성별을 선택해주세요")
    private String gender;
}
