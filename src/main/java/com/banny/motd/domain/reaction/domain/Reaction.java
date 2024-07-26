package com.banny.motd.domain.reaction.domain;

import com.banny.motd.global.enums.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {

    private Long id;
    private Long userId;
    private TargetType targetType;
    private Long targetId;
    private ReactionType reactionType;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
}
