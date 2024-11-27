package com.banny.motd.api.controller.event.response;

import com.banny.motd.domain.event.EventDetail;
import com.banny.motd.domain.event.EventType;
import com.banny.motd.domain.participation.ParticipationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventDetailResponse {

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
    private EventDetailResponse(Long id, String title, String imageUrl, String description, EventType eventType, int maxParticipants, ParticipationStatus status, LocalDateTime registerStartAt, LocalDateTime registerEndAt, LocalDateTime eventStartAt, LocalDateTime eventEndAt, RegisterUser registerUser) {
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

    public static EventDetailResponse from(EventDetail eventDetail) {
        return EventDetailResponse.builder()
                .id(eventDetail.getId())
                .title(eventDetail.getTitle())
                .description(eventDetail.getDescription())
                .eventType(eventDetail.getEventType())
                .maxParticipants(eventDetail.getMaxParticipants())
                .status(eventDetail.getStatus())
                .registerStartAt(eventDetail.getRegisterStartAt())
                .registerEndAt(eventDetail.getRegisterEndAt())
                .eventStartAt(eventDetail.getEventStartAt())
                .eventEndAt(eventDetail.getEventEndAt())
                .registerUser(RegisterUser.builder()
                        .id(eventDetail.getRegisterUser().getId())
                        .loginId(eventDetail.getRegisterUser().getLoginId())
                        .userName(eventDetail.getRegisterUser().getUsername())
                        .profileImageUrl(eventDetail.getRegisterUser().getProfileImageUrl())
                        .build())
                .build();
    }
    
}
