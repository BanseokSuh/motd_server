package com.banny.motd.domain.comment.infrastructure;

import com.banny.motd.domain.comment.Comment;
import com.banny.motd.global.enums.TargetType;

import java.util.List;

public interface CommentRepository {

    List<Comment> findByTargetIdAndTargetType(Long targetId, TargetType targetType);

    void save(Comment comment);

}
