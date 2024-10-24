package com.banny.motd.api.service.comment.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreateServiceRequest {

    private String comment;

    @Builder
    public CommentCreateServiceRequest(String comment) {
        this.comment = comment;
    }

}
