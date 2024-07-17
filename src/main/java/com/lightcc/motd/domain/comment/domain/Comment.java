package com.lightcc.motd.domain.comment.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Comment {

    private Long id;
    private Long userId;
    private Long postId;
    private String comment;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
}
