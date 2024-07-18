package com.banny.motd.domain.reaction.api;

import com.banny.motd.domain.reaction.application.ReactionService;
import com.banny.motd.domain.user.domain.User;
import com.banny.motd.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping("/like/post/{postId}")
    public Response<Void> likePost(@PathVariable Long postId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        reactionService.likePost(postId, user.getId());

        return Response.success();
    }
}
