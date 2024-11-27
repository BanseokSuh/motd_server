package com.banny.motd.api.controller.post.response;

import com.banny.motd.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostListResponse {

    private Long id;
    private List<String> imageUrls;
    private String content;
    private LocalDateTime createdAt;
    private RegisterUser registerUser;

    @Getter
    @Builder
    private static class RegisterUser {
        private Long id;
        private String loginId;
        private String userName;
        private String profileImageUrl;
    }

    @Builder
    private PostListResponse(Long id, List<String> imageUrls, String content, LocalDateTime createdAt, RegisterUser registerUser) {
        this.id = id;
        this.imageUrls = imageUrls;
        this.content = content;
        this.createdAt = createdAt;
        this.registerUser = registerUser;
    }

    public static PostListResponse from(Post post) {
        return PostListResponse.builder()
                .id(post.getId())
                .imageUrls(post.getImageUrls())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .registerUser(RegisterUser.builder()
                        .id(post.getAuthor().getId())
                        .loginId(post.getAuthor().getLoginId())
                        .userName(post.getAuthor().getUsername())
                        .profileImageUrl(post.getAuthor().getProfileImageUrl())
                        .build())
                .build();
    }

}
