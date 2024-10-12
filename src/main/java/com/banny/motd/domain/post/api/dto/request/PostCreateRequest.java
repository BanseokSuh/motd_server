package com.banny.motd.domain.post.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    @NotBlank(message = "이미지 경로를 입력해주세요.")
    @Size(min = 1, max = 200, message = "이미지 경로는 1자 이상 200자 이하로 입력해주세요.")
    private String imagePath;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 1, max = 2_000, message = "내용은 1자 이상 2,000자 이하로 입력해주세요.")
    private String content;

}
