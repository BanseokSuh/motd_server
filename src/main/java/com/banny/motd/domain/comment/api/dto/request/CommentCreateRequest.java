package com.banny.motd.domain.comment.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @NotBlank(message = "내용을 입력해주세요")
    @Size(min = 1, max = 1_000, message = "내용은 1000자 이하로 입력해주세요")
    private String comment;
}
