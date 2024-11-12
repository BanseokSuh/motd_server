package com.banny.motd.api.controller.post.response;

import com.banny.motd.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreateResponse {

    private Long id;

    @Builder
    private PostCreateResponse(Long id) {
        this.id = id;
    }

    public static PostCreateResponse from(Post post) {
        return PostCreateResponse.builder().id(post.getId()).build();
    }

}
