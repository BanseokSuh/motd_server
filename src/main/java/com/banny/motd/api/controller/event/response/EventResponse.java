package com.banny.motd.api.controller.event.response;

import com.banny.motd.api.service.event.response.EventServiceResponse;
import com.banny.motd.domain.event.EventType;
import com.banny.motd.domain.participation.ParticipationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventResponse {

    private Long id;
    private String title;
    private String imageUrl;
    private String description;
    private EventType eventType;
    private int maxParticipants;
    private ParticipationStatus status;
    private LocalDateTime registerStartAt;
    private LocalDateTime registerEndAt;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private RegisterUser registerUser;

    @Getter
    @Builder
    private static class RegisterUser {
        private Long id;
        private String loginId;
        private String userName;
        private String profileImageUrl;
    }

    @Builder
    private EventResponse(Long id, String title, String imageUrl, String description, EventType eventType, int maxParticipants, ParticipationStatus status, LocalDateTime registerStartAt, LocalDateTime registerEndAt, LocalDateTime eventStartAt, LocalDateTime eventEndAt, RegisterUser registerUser) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.eventType = eventType;
        this.maxParticipants = maxParticipants;
        this.status = status;
        this.registerStartAt = registerStartAt;
        this.registerEndAt = registerEndAt;
        this.eventStartAt = eventStartAt;
        this.eventEndAt = eventEndAt;
        this.registerUser = registerUser;
    }

    public static EventResponse from(EventServiceResponse event) {
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .eventType(event.getEventType())
                .maxParticipants(event.getMaxParticipants())
                .status(event.getStatus())
                .registerStartAt(event.getRegisterStartAt())
                .registerEndAt(event.getRegisterEndAt())
                .eventStartAt(event.getEventStartAt())
                .eventEndAt(event.getEventEndAt())
                .registerUser(RegisterUser.builder()
                        .id(event.getRegisterUser().getId())
                        .loginId(event.getRegisterUser().getLoginId())
                        .userName(event.getRegisterUser().getUsername())
                        .profileImageUrl(event.getRegisterUser().getProfileImageUrl())
                        .build())
                .build();
    }

}
