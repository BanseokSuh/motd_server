package com.lightcc.motd.domain.reaction.application;

public interface ReactionService {

    void likePost(Long postId, Long userId);
}
