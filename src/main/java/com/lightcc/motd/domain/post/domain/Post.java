package com.lightcc.motd.domain.post.domain;

import com.lightcc.motd.domain.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Post {

    private Long id;
    private String title;
    private String content;
    private User user;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
}
