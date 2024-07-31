package com.banny.motd.domain.post.domain;

import com.banny.motd.domain.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Post {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
}
