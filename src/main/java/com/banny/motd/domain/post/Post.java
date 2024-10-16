package com.banny.motd.domain.post;

import com.banny.motd.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private Long id;
    private List<String> imageUrls;
    private String content;
    private User author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public boolean isAuthor(Long userId) {
        return this.author.getId().equals(userId);
    }

    public void setPost(String content) {
        this.content = content;
    }

}
