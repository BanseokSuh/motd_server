package com.banny.motd.domain.comment.application;

import com.banny.motd.domain.comment.domain.Comment;

import java.util.List;

public interface CommentService {

    void commentPost(Long postId, Long userId, String comment);

    List<Comment> getCommentListByPostId(Long postId);
}
