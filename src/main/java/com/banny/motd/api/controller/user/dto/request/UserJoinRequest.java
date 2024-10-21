package com.banny.motd.api.controller.user.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserJoinRequest {

    @NotBlank(message = "아이디름 입력해주세요.")
    @Size(min = 4, max = 20, message = "아이디는 4~20자 사이로 입력해주세요.")
    private String loginId;

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "별명을 입력해주세요.")
    private String nickName;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대소문자, 숫자, 특수문자를 사용하세요")
    private String password;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식을 지켜주세요")
    private String email;

    @NotBlank(message = "성별을 입력해주세요.")
    private String gender;

}
