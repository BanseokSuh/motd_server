package com.banny.motd.api.controller.user;

import com.banny.motd.api.controller.user.dto.request.UserJoinRequest;
import com.banny.motd.api.controller.user.dto.request.UserLoginRequest;
import com.banny.motd.api.controller.user.dto.response.UserJoinResponse;
import com.banny.motd.api.controller.user.dto.response.UserLoginResponse;
import com.banny.motd.api.controller.user.dto.response.UserMyResponse;
import com.banny.motd.api.service.user.UserService;
import com.banny.motd.domain.user.Tokens;
import com.banny.motd.domain.user.User;
import com.banny.motd.global.dto.response.Response;
import groovy.util.logging.Slf4j;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@lombok.extern.slf4j.Slf4j
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     *
     * @param request UserJoinRequest
     * @return UserJoinResponse
     */
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@Valid @RequestBody UserJoinRequest request) {
        User user = userService.join(request.getLoginId(), request.getUserName(), request.getNickName(), request.getPassword(), request.getEmail(), request.getGender());
        return Response.success(UserJoinResponse.from(user));
    }

    /**
     * 로그인
     *
     * @param request UserLoginRequest
     * @return UserLoginResponse
     */
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        Tokens token = userService.login(request.getLoginId(), request.getPassword(), request.getDevice());
        return Response.success(UserLoginResponse.from(token));
    }

    /**
     * 로그아웃
     *
     * @param authentication Authentication
     * @return Response<Void>
     */
    @PostMapping("/logout")
    public Response<Void> logout(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        userService.logout(user.getId(), user.getDevice());
        return Response.success();
    }

    /**
     * 회원 탈퇴
     *
     * @param authentication Authentication
     * @return Response<Void>
     */
    @DeleteMapping()
    public Response<Void> delete(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userService.delete(user.getId());
        return Response.success();
    }

    /**
     * 내 정보 조회
     *
     * @param authentication Authentication
     * @return UserMyResponse
     */
    @GetMapping("/my")
    public Response<UserMyResponse> getMyInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return Response.success(UserMyResponse.from(userService.getMyInfo(user.getId())));
    }

}
