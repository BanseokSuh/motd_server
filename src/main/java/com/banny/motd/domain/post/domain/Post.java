package com.banny.motd.domain.post.domain;

import com.banny.motd.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private Long id;
    private String content;
    private User author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public boolean isAuthor(Long userId) {
        return this.author.getId().equals(userId);
    }

    public void setTitleAndContent(String content) {
        this.content = content;
    }

}
