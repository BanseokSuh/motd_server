package com.banny.motd.api.controller.post.request;

import com.banny.motd.api.service.post.request.PostModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostModifyRequest {

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 1, max = 2_000, message = "내용은 1자 이상 2,000자 이하로 입력해주세요.")
    private String content;

    public PostModifyServiceRequest toServiceRequest() {
        return PostModifyServiceRequest.builder()
                .content(content)
                .build();
    }

}
