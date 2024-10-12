package com.banny.motd.domain.post.domain;

import com.banny.motd.domain.comment.domain.Comment;
import com.banny.motd.domain.reaction.domain.Reaction;
import com.banny.motd.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostDetail {

    private Long id;
    private String content;
    private User author;
    private List<Reaction> likeList;
    private List<Comment> commentList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public PostDetail(Post post, User user, List<Comment> commentList, List<Reaction> likeList) {
        this.id = post.getId();
        this.content = post.getContent();
        this.author = user;
        this.likeList = likeList;
        this.commentList = commentList;
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.deletedAt = post.getDeletedAt();
    }

}
