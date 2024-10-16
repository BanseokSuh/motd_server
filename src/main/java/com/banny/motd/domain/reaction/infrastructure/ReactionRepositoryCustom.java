package com.banny.motd.domain.reaction.infrastructure;

import com.banny.motd.domain.reaction.ReactionType;
import com.banny.motd.domain.reaction.infrastructure.entity.ReactionEntity;
import com.banny.motd.global.enums.TargetType;

import java.util.List;

public interface ReactionRepositoryCustom {

    List<ReactionEntity> findListByTargetIdAndTargetTypeAndReactionType(Long targetId, TargetType targetType, ReactionType reactionType);

    ReactionEntity findByUserIdAndTargetTypeAndTargetIdAndReactionType(Long userId, TargetType targetType, Long targetId, ReactionType reactionType);

}
