package com.banny.motd.domain.comment.domain;

import com.banny.motd.domain.user.domain.User;
import com.banny.motd.global.enums.TargetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Long id;
    private User author;
    private TargetType targetType;
    private Long targetId;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
