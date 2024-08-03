package com.banny.motd.domain.reaction.domain;

import com.banny.motd.global.enums.TargetType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {

    private Long id;
    private Long userId;
    private TargetType targetType;
    private Long targetId;
    private ReactionType reactionType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
