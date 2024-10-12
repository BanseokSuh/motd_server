package com.banny.motd.domain.post.api.dto.response;

import com.banny.motd.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateResponse {

    private Long id;

    public static PostCreateResponse from(Post post) {
        return new PostCreateResponse(
                post.getId()
        );
    }

}
