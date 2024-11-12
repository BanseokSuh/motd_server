package com.banny.motd.api.service.event.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EventCreateServiceRequest {

    private String title;
    private String description;
    private int maxParticipants;
    private String eventType;
    private LocalDateTime registerStartAt;
    private LocalDateTime registerEndAt;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;

    @Builder
    public EventCreateServiceRequest(String title, String description, int maxParticipants, String eventType, LocalDateTime registerStartAt, LocalDateTime registerEndAt, LocalDateTime eventStartAt, LocalDateTime eventEndAt, Long userId) {
        this.title = title;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.eventType = eventType;
        this.registerStartAt = registerStartAt;
        this.registerEndAt = registerEndAt;
        this.eventStartAt = eventStartAt;
        this.eventEndAt = eventEndAt;
    }

}
