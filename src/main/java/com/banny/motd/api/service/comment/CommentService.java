package com.banny.motd.api.service.comment;

import com.banny.motd.domain.comment.Comment;

import java.util.List;

public interface CommentService {

    void commentPost(Long postId, Long userId, String comment);

    List<Comment> getCommentListByPostId(Long postId);

}
