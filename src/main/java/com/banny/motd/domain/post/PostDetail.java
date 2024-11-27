package com.banny.motd.domain.post;

import com.banny.motd.domain.comment.Comment;
import com.banny.motd.domain.reaction.Reaction;
import com.banny.motd.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostDetail {

    private Long id;
    private List<String> imageUrls;
    private String content;
    private User registerUser;
    private List<Reaction> likeList;
    private List<Comment> commentList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public PostDetail(Post post, User user, List<Comment> commentList, List<Reaction> likeList) {
        this.id = post.getId();
        this.imageUrls = post.getImageUrls();
        this.content = post.getContent();
        this.registerUser = user;
        this.likeList = likeList;
        this.commentList = commentList;
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.deletedAt = post.getDeletedAt();
    }

}
