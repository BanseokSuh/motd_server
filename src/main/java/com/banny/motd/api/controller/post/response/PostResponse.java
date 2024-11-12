package com.banny.motd.api.controller.post.response;

import com.banny.motd.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long writerUserId;

    @Builder
    private PostResponse(Long id, String content, LocalDateTime createdAt, Long writerUserId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.writerUserId = writerUserId;
    }

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .writerUserId(post.getAuthor().getId())
                .build();
    }

}
