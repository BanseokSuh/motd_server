package com.banny.motd.domain.post.api.dto.response;

import com.banny.motd.domain.post.domain.PostAuthor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class PostAuthorResponse {
    private Long id;
    private String title;
    private String content;
    private Timestamp createdAt;
    private AuthorResponse author;

    @Getter
    @Builder
    public static class AuthorResponse {
        private Long id;
        private String loginId;
        private String userName;
    }

    public static PostAuthorResponse from(PostAuthor postAuthor) {
        return new PostAuthorResponse(
                postAuthor.getId(),
                postAuthor.getTitle(),
                postAuthor.getContent(),
                postAuthor.getCreatedAt(),
                AuthorResponse.builder()
                        .id(postAuthor.getAuthor().getId())
                        .loginId(postAuthor.getAuthor().getLoginId())
                        .userName(postAuthor.getAuthor().getUsername())
                        .build()
        );
    }
}
