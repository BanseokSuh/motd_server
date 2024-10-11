package com.banny.motd.domain.comment.infrastructure;

import com.banny.motd.domain.comment.infrastructure.entity.CommentEntity;
import com.banny.motd.global.enums.TargetType;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentEntity> findByTargetIdAndTargetType(Long targetId, TargetType targetType);

}
