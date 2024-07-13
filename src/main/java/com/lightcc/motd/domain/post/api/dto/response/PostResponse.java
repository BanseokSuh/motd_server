package com.lightcc.motd.domain.post.api.dto.response;

import com.lightcc.motd.domain.post.domain.Post;
import com.lightcc.motd.domain.user.api.dto.response.UserResponse;
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
    private UserResponse user;

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                UserResponse.from(post.getUser())
        );
    }
}
