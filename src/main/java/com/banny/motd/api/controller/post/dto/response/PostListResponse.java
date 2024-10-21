package com.banny.motd.api.controller.post.dto.response;

import com.banny.motd.domain.post.PostList;
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

    public static PostListResponse from(PostList postList) {
        return new PostListResponse(
                postList.getId(),
                postList.getImageUrls(),
                postList.getContent(),
                postList.getCreatedAt(),
                AuthorResponse.builder()
                        .id(postList.getAuthor().getId())
                        .loginId(postList.getAuthor().getLoginId())
                        .userName(postList.getAuthor().getUsername())
                        .profileImageUrl(postList.getAuthor().getProfileImageUrl())
                        .build()
        );
    }

}
