package com.banny.motd.domain.user.api.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserJoinRequest {

    @NotBlank(message = "Please enter your ID")
    @Size(min = 4, max = 20, message = "ID must be between 4 and 20 characters")
    private String loginId;

    @NotBlank(message = "Please enter your name")
    private String userName;

    @NotBlank(message = "Please enter your password")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대소문자, 숫자, 특수문자를 사용하세요")
    private String password;

    @NotBlank(message = "Please enter your email")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식을 지켜주세요")
    private String email;

    @NotBlank(message = "Please select your gender")
    private String gender;

}
