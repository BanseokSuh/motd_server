package com.banny.motd.domain.reaction.infrastructure;

import com.banny.motd.domain.reaction.Reaction;
import com.banny.motd.domain.reaction.ReactionType;
import com.banny.motd.global.enums.TargetType;

import java.util.List;

public interface ReactionRepository {

    List<Reaction> findListByTargetIdAndTargetTypeAndReactionType(Long targetId, TargetType targetType, ReactionType reactionType);

    Reaction findByUserIdAndTargetTypeAndTargetIdAndReactionType(Long userId, TargetType targetType, Long targetId, ReactionType reactionType);

    void save(Reaction reaction);

    void delete(Reaction reaction);

}
