package com.banny.motd.domain.comment.domain;

import com.banny.motd.global.enums.TargetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
