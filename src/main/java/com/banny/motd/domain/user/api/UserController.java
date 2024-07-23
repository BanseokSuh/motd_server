package com.banny.motd.domain.user.api;

import com.banny.motd.domain.user.api.dto.request.UserJoinRequest;
import com.banny.motd.domain.user.api.dto.request.UserLoginRequest;
import com.banny.motd.domain.user.api.dto.response.UserJoinResponse;
import com.banny.motd.domain.user.api.dto.response.UserLoginResponse;
import com.banny.motd.domain.user.application.UserService;
import com.banny.motd.domain.user.domain.Tokens;
import com.banny.motd.domain.user.domain.User;
import com.banny.motd.global.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@Valid @RequestBody UserJoinRequest request) {
        User user = userService.join(request.getLoginId(), request.getUserName(), request.getPassword(), request.getGender());
        return Response.success(UserJoinResponse.from(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        Tokens token = userService.login(request.getLoginId(), request.getPassword());
        return Response.success(UserLoginResponse.from(token));
    }
}
