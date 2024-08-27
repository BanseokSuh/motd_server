package com.banny.motd.domain.user.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserLoginRequest {

    @NotBlank(message = "Please enter your ID")
    @Size(min = 4, max = 20, message = "ID must be between 4 and 20 characters")
    private String loginId;

    @NotBlank(message = "Please enter your password")
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    private String password;

    @NotBlank(message = "Please enter the device type")
    private String deviceType;

}