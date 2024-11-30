package com.banny.motd.api.service.event.response;

import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.event.EventType;
import com.banny.motd.domain.participation.ParticipationStatus;
import com.banny.motd.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventServiceResponse {

    private Long id;
    private String title;
    private String imageUrl;
    private String description;
    private EventType eventType;
    private User registerUser;
    private int maxParticipants;
    private ParticipationStatus status;
    private LocalDateTime registerStartAt;
    private LocalDateTime registerEndAt;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;

    @Builder
    private EventServiceResponse(Long id, String title, String imageUrl, String description, EventType eventType, User registerUser, int maxParticipants, ParticipationStatus status, LocalDateTime registerStartAt, LocalDateTime registerEndAt, LocalDateTime eventStartAt, LocalDateTime eventEndAt) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.eventType = eventType;
        this.registerUser = registerUser;
        this.maxParticipants = maxParticipants;
        this.status = status;
        this.registerStartAt = registerStartAt;
        this.registerEndAt = registerEndAt;
        this.eventStartAt = eventStartAt;
        this.eventEndAt = eventEndAt;
    }

    public static EventServiceResponse from(Event event, User registerUser, ParticipationStatus status) {
        return EventServiceResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .imageUrl(event.getImageUrl())
                .description(event.getDescription())
                .eventType(event.getEventType())
                .registerUser(registerUser)
                .maxParticipants(event.getMaxParticipants())
                .status(status)
                .registerStartAt(event.getRegisterStartAt())
                .registerEndAt(event.getRegisterEndAt())
                .eventStartAt(event.getEventStartAt())
                .eventEndAt(event.getEventEndAt())
                .build();
    }

}
