package com.banny.motd.domain.reaction.domain.repository;

import com.banny.motd.domain.reaction.domain.ReactionType;
import com.banny.motd.domain.reaction.infrastructure.entity.ReactionEntity;
import com.banny.motd.global.enums.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {
    ReactionEntity findByUserIdAndTargetTypeAndTargetIdAndReactionType(Long userId, TargetType targetType, Long targetId, ReactionType reactionType);
}
