package com.lightcc.motd.domain.reaction.domain;

import com.lightcc.motd.global.enums.TargetType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
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
