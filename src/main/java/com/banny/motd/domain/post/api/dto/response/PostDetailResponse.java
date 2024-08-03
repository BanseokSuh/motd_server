package com.banny.motd.domain.post.api.dto.response;

import com.banny.motd.domain.post.domain.PostDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private AuthorResponse author;
    private List<LikeResponse> likeList;
    private Integer likeCount;
    private List<CommentResponse> commentList;
    private Integer commentCount;

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

    @Getter
    @Builder
    private static class LikeResponse {
        private Long id;
        private LocalDateTime createdAt;
        private AuthorResponse author;
    }

    public static PostDetailResponse from(PostDetail postDetail) {
        return new PostDetailResponse(
                postDetail.getId(),
                postDetail.getTitle(),
                postDetail.getContent(),
                postDetail.getCreatedAt(),
                AuthorResponse.builder() // 게시글 작성자
                        .id(postDetail.getAuthor().getId()) // 게시글 작성자 고유값
                        .loginId(postDetail.getAuthor().getLoginId()) // 게시글 작성자 아이디
                        .userName(postDetail.getAuthor().getUsername()) // 게시글 작성자 이름
                        .build(),
                postDetail.getLikeList().stream() // 좋아요 목록
                        .map(reaction -> LikeResponse.builder() // 개별 좋아요 인스턴스
                                .id(reaction.getId()) // 식별값
                                .createdAt(reaction.getCreatedAt()) // 좋아요 생성일
                                .author(AuthorResponse.builder() // 좋아요 작성자
                                        .id(reaction.getAuthor().getId()) // 좋아요 작성자 식별값
                                        .loginId(reaction.getAuthor().getLoginId()) // 좋아요 작성자 아이디
                                        .userName(reaction.getAuthor().getUsername()) // 좋아요 작성자 이름
                                        .build())
                                .build())
                        .toList(),
                postDetail.getLikeList().size(), // 좋아요 개수
                postDetail.getCommentList().stream() // 댓글 목록
                        .map(comment -> CommentResponse.builder() // 개별 댓글 인스턴스
                                .id(comment.getId()) // 식별값
                                .content(comment.getComment()) // 댓글 내용
                                .createdAt(comment.getCreatedAt()) // 댓글 생성일
                                .author(AuthorResponse.builder() // 댓글 작성자
                                        .id(comment.getAuthor().getId()) // 댓글 작성자 식별값
                                        .loginId(comment.getAuthor().getLoginId()) // 댓글 작성자 아이디
                                        .userName(comment.getAuthor().getUsername()) // 댓글 작성자 이름
                                        .build())
                                .build())
                        .toList(),
                postDetail.getCommentList().size() // 댓글 개수
        );
    }
}
