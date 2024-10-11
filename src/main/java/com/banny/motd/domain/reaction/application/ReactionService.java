package com.banny.motd.domain.reaction.application;

import com.banny.motd.domain.reaction.domain.Reaction;

import java.util.List;

public interface ReactionService {

    void likePost(Long postId, Long userId);

    List<Reaction> getLikeListByPostId(Long postId);

}
