package com.banny.motd.domain.post;

import com.banny.motd.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Post {

    private Long id;
    private List<String> imageUrls;
    private String content;
    private User author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public Post(Long id, List<String> imageUrls, String content, User author, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.imageUrls = imageUrls;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public boolean isAuthor(Long userId) {
        return this.author.getId().equals(userId);
    }

    public void setPost(String content) {
        this.content = content;
    }

}
