package com.lightcc.motd.domain.user.api;

import com.lightcc.motd.domain.user.api.dto.request.UserJoinRequest;
import com.lightcc.motd.domain.user.api.dto.response.UserJoinResponse;
import com.lightcc.motd.domain.user.application.UserService;

import com.lightcc.motd.domain.user.domain.User;
import com.lightcc.motd.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    public Response<String> helloUser() {
        String helloUser = userService.helloUser();
        return Response.success(helloUser);
    }

    @GetMapping("/hello-exception")
    public Response<String> helloUserException() {
        String helloUser = userService.helloUserException();
        return Response.success(helloUser);
    }

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        User user = userService.join(request.getLoginId(), request.getUserName(), request.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }
}
