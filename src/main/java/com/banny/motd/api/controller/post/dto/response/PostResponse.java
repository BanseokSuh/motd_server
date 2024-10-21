package com.banny.motd.api.controller.post.dto.response;

import com.banny.motd.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long writerUserId;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .writerUserId(post.getAuthor().getId())
                .build();
    }

}
