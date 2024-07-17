package com.lightcc.motd.domain.comment.domain;

import com.lightcc.motd.domain.post.domain.Post;
import com.lightcc.motd.domain.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Comment {

    private Long id;
    private User user;
    private Post post;
    private String comment;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
}
