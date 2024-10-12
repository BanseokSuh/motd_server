package com.banny.motd.domain.post.domain;

import com.banny.motd.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostList {

    private Long id;
    private String content;
    private User author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public PostList(Post post, User user) {
        this.id = post.getId();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.deletedAt = post.getDeletedAt();
        this.author = user;
    }

}
