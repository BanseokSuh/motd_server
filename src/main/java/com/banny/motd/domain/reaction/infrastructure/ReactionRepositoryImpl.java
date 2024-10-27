package com.banny.motd.domain.reaction.infrastructure;

import com.banny.motd.domain.reaction.Reaction;
import com.banny.motd.domain.reaction.ReactionType;
import com.banny.motd.domain.reaction.infrastructure.entity.QReactionEntity;
import com.banny.motd.domain.reaction.infrastructure.entity.ReactionEntity;
import com.banny.motd.global.enums.TargetType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReactionRepositoryImpl implements ReactionRepository {

    private final ReactionJpaRepository reactionJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Reaction> findListByTargetIdAndTargetTypeAndReactionType(Long targetId, TargetType targetType, ReactionType reactionType) {
        return jpaQueryFactory
                .selectFrom(QReactionEntity.reactionEntity)
                .where(QReactionEntity.reactionEntity.targetId.eq(targetId)
                        .and(QReactionEntity.reactionEntity.targetType.eq(targetType))
                        .and(QReactionEntity.reactionEntity.reactionType.eq(reactionType)))
                .stream().map(ReactionEntity::toDomain)
                .toList();
    }

    @Override
    public Reaction findByUserIdAndTargetTypeAndTargetIdAndReactionType(Long userId, TargetType targetType, Long targetId, ReactionType reactionType) {
        return jpaQueryFactory
                .selectFrom(QReactionEntity.reactionEntity)
                .where(QReactionEntity.reactionEntity.user.id.eq(userId)
                        .and(QReactionEntity.reactionEntity.targetType.eq(targetType))
                        .and(QReactionEntity.reactionEntity.targetId.eq(targetId))
                        .and(QReactionEntity.reactionEntity.reactionType.eq(reactionType)))
                .stream().map(ReactionEntity::toDomain)
                .findFirst().orElse(null);
    }

    @Override
    public void save(Reaction reaction) {
        reactionJpaRepository.save(ReactionEntity.from(reaction));
    }

    @Override
    public void delete(Reaction reaction) {
        reactionJpaRepository.delete(ReactionEntity.from(reaction));
    }

}
