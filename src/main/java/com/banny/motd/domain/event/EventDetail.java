package com.banny.motd.domain.event;

import com.banny.motd.domain.participation.ParticipationStatus;
import com.banny.motd.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EventDetail {

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
    public EventDetail(Long id, String title, String imageUrl, String description, EventType eventType, User registerUser, int maxParticipants, ParticipationStatus status, LocalDateTime registerStartAt, LocalDateTime registerEndAt, LocalDateTime eventStartAt, LocalDateTime eventEndAt) {
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

    @Builder
    public EventDetail(Event event, User registerUser, ParticipationStatus status) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.imageUrl = event.getImageUrl();
        this.description = event.getDescription();
        this.eventType = event.getEventType();
        this.registerUser = registerUser;
        this.maxParticipants = event.getMaxParticipants();
        this.status = status;
        this.registerStartAt = event.getRegisterStartAt();
        this.registerEndAt = event.getRegisterEndAt();
        this.eventStartAt = event.getEventStartAt();
        this.eventEndAt = event.getEventEndAt();
    }

}
