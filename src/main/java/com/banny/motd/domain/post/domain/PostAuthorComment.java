package com.banny.motd.domain.post.domain;

import com.banny.motd.domain.comment.domain.Comment;
import com.banny.motd.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostAuthorComment {

    private Long id;
    private String title;
    private String content;
    private User author;
    private List<Comment> commentList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public PostAuthorComment(Post post, User user, List<Comment> commentList) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = user;
        this.commentList = commentList;
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.deletedAt = post.getDeletedAt();
    }
}
