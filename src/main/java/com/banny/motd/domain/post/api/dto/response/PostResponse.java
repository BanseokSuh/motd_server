package com.banny.motd.domain.post.api.dto.response;

import com.banny.motd.domain.post.domain.Post;
import com.banny.motd.domain.user.api.dto.response.UserResponse;
import com.banny.motd.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Long writerUserId;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .writerUserId(post.getUserId())
                .build();
    }
}
