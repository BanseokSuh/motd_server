package com.banny.motd.domain.comment.infrastructure;

import com.banny.motd.domain.comment.Comment;
import com.banny.motd.domain.comment.infrastructure.entity.CommentEntity;
import com.banny.motd.domain.comment.infrastructure.entity.QCommentEntity;
import com.banny.motd.global.enums.TargetType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> findByTargetIdAndTargetType(Long targetId, TargetType targetType) {
        return jpaQueryFactory
                .selectFrom(QCommentEntity.commentEntity)
                .where(QCommentEntity.commentEntity.targetId.eq(targetId)
                        .and(QCommentEntity.commentEntity.targetType.eq(targetType)))
                .stream().map(CommentEntity::toDomain)
                .toList();
    }

    @Override
    public void save(Comment comment) {
        commentJpaRepository.save(CommentEntity.from(comment));
    }

    @Override
    public void deleteAllInBatch() {
        commentJpaRepository.deleteAllInBatch();
    }

}
