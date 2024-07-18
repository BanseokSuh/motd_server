package com.banny.motd.domain.comment.domain;

import com.banny.motd.global.enums.TargetType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Comment {

    private Long id;
    private Long userId;
    private TargetType targetType;
    private Long targetId;
    private String comment;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
}
