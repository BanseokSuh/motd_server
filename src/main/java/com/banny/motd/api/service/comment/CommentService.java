package com.banny.motd.api.service.comment;

import com.banny.motd.api.service.comment.request.CommentCreateServiceRequest;
import com.banny.motd.domain.comment.Comment;

import java.util.List;

public interface CommentService {

    void createPostComment(Long postId, Long userId, CommentCreateServiceRequest request);

    List<Comment> getCommentListByPostId(Long postId);

}
