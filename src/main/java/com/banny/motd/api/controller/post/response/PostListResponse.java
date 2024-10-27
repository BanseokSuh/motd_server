package com.banny.motd.api.controller.post.response;

import com.banny.motd.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostListResponse {

    private Long id;
    private List<String> imageUrls;
    private String content;
    private LocalDateTime createdAt;
    private AuthorResponse author;

    @Getter
    @Builder
    public static class AuthorResponse {
        private Long id;
        private String loginId;
        private String userName;
        private String profileImageUrl;
    }

    public static PostListResponse from(Post post) {
        return new PostListResponse(
                post.getId(),
                post.getImageUrls(),
                post.getContent(),
                post.getCreatedAt(),
                AuthorResponse.builder()
                        .id(post.getAuthor().getId())
                        .loginId(post.getAuthor().getLoginId())
                        .userName(post.getAuthor().getUsername())
                        .profileImageUrl(post.getAuthor().getProfileImageUrl())
                        .build()
        );
    }

}
