package com.lightcc.motd.domain.like.application;

public interface LikeService {

    void likePost(Long postId, Long userId);
}
