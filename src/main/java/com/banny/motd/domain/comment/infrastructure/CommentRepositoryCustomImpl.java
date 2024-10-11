package com.banny.motd.domain.comment.infrastructure;

import com.banny.motd.domain.comment.infrastructure.entity.CommentEntity;
import com.banny.motd.domain.comment.infrastructure.entity.QCommentEntity;
import com.banny.motd.global.enums.TargetType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CommentEntity> findByTargetIdAndTargetType(Long targetId, TargetType targetType) {
        return jpaQueryFactory
                .selectFrom(QCommentEntity.commentEntity)
                .where(QCommentEntity.commentEntity.targetId.eq(targetId)
                        .and(QCommentEntity.commentEntity.targetType.eq(targetType)))
                .fetch();
    }

}
