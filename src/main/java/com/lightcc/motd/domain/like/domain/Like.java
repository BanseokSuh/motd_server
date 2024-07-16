package com.lightcc.motd.domain.like.domain;

import com.lightcc.motd.domain.post.domain.Post;
import com.lightcc.motd.domain.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Like {

    private Long id;
    private User user;
    private Post post;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
}
