package com.banny.motd.api.controller.event.response;


import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.event.EventType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class EventListResponse {

    private Long id;
    private String title;
    private String imageUrl;
    private EventType eventType;
    private int maxParticipants;
    private List<Long> participantsIds;
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
    private EventListResponse(Long id, String title, String imageUrl, EventType eventType, int maxParticipants, List<Long> participantsIds, LocalDateTime registerStartAt, LocalDateTime registerEndAt, LocalDateTime eventStartAt, LocalDateTime eventEndAt, RegisterUser registerUser) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.eventType = eventType;
        this.maxParticipants = maxParticipants;
        this.participantsIds = participantsIds;
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
                .eventType(event.getEventType())
                .maxParticipants(event.getMaxParticipants())
                .participantsIds(event.getParticipantsIds())
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
