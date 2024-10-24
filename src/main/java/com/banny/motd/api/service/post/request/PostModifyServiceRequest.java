package com.banny.motd.api.service.post.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostModifyServiceRequest {

    private String content;

    @Builder
    public PostModifyServiceRequest(String content) {
        this.content = content;
    }

}
