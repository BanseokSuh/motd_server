package com.banny.motd.domain.reaction.infrastructure;

import com.banny.motd.domain.reaction.ReactionType;
import com.banny.motd.domain.reaction.infrastructure.entity.QReactionEntity;
import com.banny.motd.domain.reaction.infrastructure.entity.ReactionEntity;
import com.banny.motd.global.enums.TargetType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReactionRepositoryCustomImpl implements ReactionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReactionEntity> findListByTargetIdAndTargetTypeAndReactionType(Long targetId, TargetType targetType, ReactionType reactionType) {
        return jpaQueryFactory
                .selectFrom(QReactionEntity.reactionEntity)
                .where(QReactionEntity.reactionEntity.targetId.eq(targetId)
                        .and(QReactionEntity.reactionEntity.targetType.eq(targetType))
                        .and(QReactionEntity.reactionEntity.reactionType.eq(reactionType)))
                .fetch();
    }

    @Override
    public ReactionEntity findByUserIdAndTargetTypeAndTargetIdAndReactionType(Long userId, TargetType targetType, Long targetId, ReactionType reactionType) {
        return jpaQueryFactory
                .selectFrom(QReactionEntity.reactionEntity)
                .where(QReactionEntity.reactionEntity.user.id.eq(userId)
                        .and(QReactionEntity.reactionEntity.targetType.eq(targetType))
                        .and(QReactionEntity.reactionEntity.targetId.eq(targetId))
                        .and(QReactionEntity.reactionEntity.reactionType.eq(reactionType)))
                .fetchOne();
    }
    
}
