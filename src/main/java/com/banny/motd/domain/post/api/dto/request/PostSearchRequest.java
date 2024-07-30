package com.banny.motd.domain.post.api.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSearchRequest {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    public long getOffset() {
        return (long) (Math.max(1, page) - 1) * Math.min(size, MAX_SIZE);
    }
}
