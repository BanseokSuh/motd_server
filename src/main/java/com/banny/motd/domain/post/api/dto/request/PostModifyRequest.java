package com.banny.motd.domain.post.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostModifyRequest {

    @NotBlank(message = "제목을 입력해주세요")
    @Size(min = 1, max = 100, message = "제목은 100자 이하로 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    @Size(min = 1, max = 2_000, message = "내용은 2000자 이하로 입력해주세요")
    private String content;
}
