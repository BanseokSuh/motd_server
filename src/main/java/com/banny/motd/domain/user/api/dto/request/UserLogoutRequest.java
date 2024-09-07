package com.banny.motd.domain.user.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserLogoutRequest {

    @NotBlank(message = "Please enter the device type")
    private String deviceType;

}