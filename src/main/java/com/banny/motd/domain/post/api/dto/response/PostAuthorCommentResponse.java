package com.banny.motd.domain.post.api.dto.response;

import com.banny.motd.domain.post.domain.PostAuthorComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostAuthorCommentResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private AuthorResponse author;
    private List<CommentResponse> commentList;

    @Getter
    @Builder
    private static class AuthorResponse {
        private Long id;
        private String loginId;
        private String userName;
    }

    @Getter
    @Builder
    private static class CommentResponse {
        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private AuthorResponse author;
    }

    public static PostAuthorCommentResponse from(PostAuthorComment postAuthorComment) {
        return new PostAuthorCommentResponse(
                postAuthorComment.getId(),
                postAuthorComment.getTitle(),
                postAuthorComment.getContent(),
                postAuthorComment.getCreatedAt(),
                AuthorResponse.builder() // 작성자
                        .id(postAuthorComment.getAuthor().getId())
                        .loginId(postAuthorComment.getAuthor().getLoginId())
                        .userName(postAuthorComment.getAuthor().getUsername())
                        .build(),
                postAuthorComment.getCommentList().stream() // 댓글 목록
                        .map(comment -> CommentResponse.builder() // 개별 댓글 인스턴스 생성
                                .id(comment.getId())
                                .content(comment.getComment())
                                .createdAt(comment.getCreatedAt())
                                .author(AuthorResponse.builder() // 댓글 작성자
                                        .id(comment.getAuthor().getId())
                                        .loginId(comment.getAuthor().getLoginId())
                                        .userName(comment.getAuthor().getUsername())
                                        .build())
                                .build())
                        .toList()
        );
    }
}
