package com.banny.motd.api.controller.event.response;


import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.event.EventType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventListResponse {

    private Long id;
    private String title;
    private String location;
    private String imageUrl;
    private String description;
    private EventType eventType;
    private int maxParticipants;
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
    private EventListResponse(Long id, String title, String location, String imageUrl, String description, EventType eventType, int maxParticipants, LocalDateTime registerStartAt, LocalDateTime registerEndAt, LocalDateTime eventStartAt, LocalDateTime eventEndAt, RegisterUser registerUser) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.imageUrl = imageUrl;
        this.description = description;
        this.eventType = eventType;
        this.maxParticipants = maxParticipants;
        this.registerStartAt = registerStartAt;
        this.registerEndAt = registerEndAt;
        this.eventStartAt = eventStartAt;
        this.eventEndAt = eventEndAt;
        this.registerUser = registerUser;
    }

    public static EventListResponse from(Event event) {
        return EventListResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .imageUrl(event.getImageUrl())
                .description(event.getDescription())
                .eventType(event.getEventType())
                .maxParticipants(event.getMaxParticipants())
                .registerStartAt(event.getRegisterStartAt())
                .registerEndAt(event.getRegisterEndAt())
                .eventStartAt(event.getEventStartAt())
                .eventEndAt(event.getEventEndAt())
//                .registerUser(RegisterUser.builder()
//                        .id(user.getId())
//                        .loginId(user.getLoginId())
//                        .userName(user.getUsername())
//                        .profileImageUrl(user.getProfileImageUrl())
//                        .build())
                .build();
    }

}
