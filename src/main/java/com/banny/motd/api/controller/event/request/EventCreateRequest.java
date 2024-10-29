package com.banny.motd.api.controller.event.request;

import com.banny.motd.api.service.event.request.EventCreateServiceRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EventCreateRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "설명을 입력해주세요.")
    private String description;

    @Min(value = 1, message = "참가 인원은 1명 이상으로 입력해주세요.")
    private int enrollmentLimit;

    @NotBlank(message = "이벤트 타입을 입력해주세요.")
    private String eventType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerStartAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerEndAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventStartAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventEndAt;

    public EventCreateServiceRequest toServiceRequest() {
        return EventCreateServiceRequest.builder()
                .title(title)
                .description(description)
                .enrollmentLimit(enrollmentLimit)
                .eventType(eventType)
                .registerStartAt(registerStartAt)
                .registerEndAt(registerEndAt)
                .eventStartAt(eventStartAt)
                .eventEndAt(eventEndAt)
                .build();
    }

}
