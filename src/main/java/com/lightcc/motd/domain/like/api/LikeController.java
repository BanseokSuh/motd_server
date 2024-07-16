package com.lightcc.motd.domain.like.api;

import com.lightcc.motd.domain.like.application.LikeService;
import com.lightcc.motd.domain.user.domain.User;
import com.lightcc.motd.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/post/{postId}")
    public Response<Void> likePost(@PathVariable Long postId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        likeService.likePost(postId, user.getId());

        return Response.success();
    }
}
