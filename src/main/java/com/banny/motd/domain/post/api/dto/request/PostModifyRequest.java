package com.banny.motd.domain.post.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostModifyRequest {

    @NotBlank(message = "Please enter the title")
    @Size(min = 1, max = 100, message = "The title must be between 1 and 100 characters")
    private String title;

    @NotBlank(message = "Please enter the content")
    @Size(min = 1, max = 2_000, message = "The content must be between 1 and 2,000 characters")
    private String content;

}
