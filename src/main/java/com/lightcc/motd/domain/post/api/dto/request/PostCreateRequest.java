package com.lightcc.motd.domain.post.api.dto.request;

import lombok.Getter;

@Getter
public class PostCreateRequest {

    private String title;
    private String content;
}
