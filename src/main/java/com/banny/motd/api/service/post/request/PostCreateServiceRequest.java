package com.banny.motd.api.service.post.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PostCreateServiceRequest {

    private List<String> imageUrls;
    private String content;

    @Builder
    public PostCreateServiceRequest(List<String> imageUrls, String content) {
        this.imageUrls = imageUrls;
        this.content = content;
    }
}
