package com.banny.motd.api.controller.user;

import com.banny.motd.api.controller.user.request.UserJoinRequest;
import com.banny.motd.api.controller.user.request.UserLoginRequest;
import com.banny.motd.api.controller.user.response.UserJoinResponse;
import com.banny.motd.api.controller.user.response.UserLoginResponse;
import com.banny.motd.api.controller.user.response.UserMyResponse;
import com.banny.motd.api.service.user.UserService;
import com.banny.motd.domain.user.Tokens;
import com.banny.motd.domain.user.User;
import com.banny.motd.global.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ApiResponse<UserJoinResponse> join(@Valid @RequestBody UserJoinRequest request) {
        User user = userService.join(request.toServiceRequest());

        return ApiResponse.ok(UserJoinResponse.from(user));
    }

    @PostMapping("/login")
    public ApiResponse<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        Tokens token = userService.login(request.toServiceRequest());

        return ApiResponse.ok(UserLoginResponse.from(token));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userService.logout(user.getId(), user.getDevice());

        return ApiResponse.ok();
    }

    @DeleteMapping()
    public ApiResponse<Void> delete(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userService.delete(user.getId());

        return ApiResponse.ok();
    }

    @GetMapping("/my")
    public ApiResponse<UserMyResponse> getMyInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User myUser = userService.getMyInfo(user.getId());

        return ApiResponse.ok(UserMyResponse.from(myUser));
    }

}
