package com.banny.motd.domain.comment.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @NotBlank(message = "Please enter the content")
    @Size(min = 1, max = 1_000, message = "The content must be between 1 and 1,000 characters")
    private String comment;
}
