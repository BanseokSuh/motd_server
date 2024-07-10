package com.lightcc.motd.domain.user.api;

import com.lightcc.motd.domain.user.application.UserService;

import com.lightcc.motd.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
