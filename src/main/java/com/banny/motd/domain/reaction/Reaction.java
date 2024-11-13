package com.banny.motd.domain.reaction;

import com.banny.motd.domain.user.User;
import com.banny.motd.global.enums.TargetType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Reaction {

    private Long id;
    private User author;
    private TargetType targetType;
    private Long targetId;
    private ReactionType reactionType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public Reaction(Long id, User author, TargetType targetType, Long targetId, ReactionType reactionType, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.author = author;
        this.targetType = targetType;
        this.targetId = targetId;
        this.reactionType = reactionType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

}
