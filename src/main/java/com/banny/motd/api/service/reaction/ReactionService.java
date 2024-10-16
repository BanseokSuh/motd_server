package com.banny.motd.api.service.reaction;

import com.banny.motd.domain.reaction.Reaction;

import java.util.List;

public interface ReactionService {

    void likePost(Long postId, Long userId);

    List<Reaction> getLikeListByPostId(Long postId);

}
