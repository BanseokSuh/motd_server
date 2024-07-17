package com.lightcc.motd.domain.reaction.domain.repository;

import com.lightcc.motd.domain.reaction.domain.ReactionType;
import com.lightcc.motd.global.enums.TargetType;
import com.lightcc.motd.domain.reaction.infrastructure.entity.ReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {
    ReactionEntity findByUserIdAndTargetTypeAndTargetIdAndReactionType(Long userId, TargetType targetType, Long targetId, ReactionType reactionType);
}
