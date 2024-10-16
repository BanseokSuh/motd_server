package com.banny.motd.domain.reaction;

import com.banny.motd.domain.user.User;
import com.banny.motd.global.enums.TargetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
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

}
