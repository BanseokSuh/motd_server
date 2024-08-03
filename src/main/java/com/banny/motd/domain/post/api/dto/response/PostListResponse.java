package com.banny.motd.domain.post.api.dto.response;

import com.banny.motd.domain.post.domain.PostList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostListResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private AuthorResponse author;

    @Getter
    @Builder
    public static class AuthorResponse {
        private Long id;
        private String loginId;
        private String userName;
    }

    public static PostListResponse from(PostList postList) {
        return new PostListResponse(
                postList.getId(),
                postList.getTitle(),
                postList.getContent(),
                postList.getCreatedAt(),
                AuthorResponse.builder()
                        .id(postList.getAuthor().getId())
                        .loginId(postList.getAuthor().getLoginId())
                        .userName(postList.getAuthor().getUsername())
                        .build()
        );
    }
}
