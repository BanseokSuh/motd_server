package com.lightcc.motd.domain.comment.application;

public interface CommentService {

    void commentPost(Long postId, Long userId, String comment);
}
