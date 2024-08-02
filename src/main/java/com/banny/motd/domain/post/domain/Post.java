package com.banny.motd.domain.post.domain;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
public class Post {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;


}
