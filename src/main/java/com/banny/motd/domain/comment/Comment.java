package com.banny.motd.domain.comment;

import com.banny.motd.domain.user.User;
import com.banny.motd.global.enums.TargetType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Comment {

    private Long id;
    private User author;
    private TargetType targetType;
    private Long targetId;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public Comment(Long id, User author, TargetType targetType, Long targetId, String comment, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.author = author;
        this.targetType = targetType;
        this.targetId = targetId;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

}
