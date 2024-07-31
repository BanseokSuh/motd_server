package com.banny.motd.domain.post.api.dto.response;

import com.banny.motd.domain.post.domain.Post;
import com.banny.motd.domain.user.api.dto.response.UserResponse;
import com.banny.motd.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Long userId;

    @Getter
    @AllArgsConstructor
    public static class UserResponse {
        private Long id;
        private String loginId;
    }

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUserId()
        );
    }

    public static UserResponse getWriter(User user) {
        return new UserResponse(
                user.getId(),
                user.getLoginId()
        );
    }
}
