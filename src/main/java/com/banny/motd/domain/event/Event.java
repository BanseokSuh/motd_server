package com.banny.motd.domain.event;

import com.banny.motd.domain.user.User;
import com.banny.motd.global.exception.ApiStatusType;
import com.banny.motd.global.exception.ApplicationException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Event {

    private Long id;
    private String title;
    private String description;
    private EventType eventType;
    private User manager;
    private int maxParticipants;
    private List<Long> participantsIds;
    private LocalDateTime registerStartAt;
    private LocalDateTime registerEndAt;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public Event(Long id, String title, String description, EventType eventType, User manager, int maxParticipants, List<Long> participantsIds, LocalDateTime registerStartAt, LocalDateTime registerEndAt, LocalDateTime eventStartAt, LocalDateTime eventEndAt, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.eventType = eventType;
        this.manager = manager;
        this.maxParticipants = maxParticipants;
        this.participantsIds = participantsIds;
        this.registerStartAt = registerStartAt;
        this.registerEndAt = registerEndAt;
        this.eventStartAt = eventStartAt;
        this.eventEndAt = eventEndAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void registerParticipant(List<Long> participantsIds) {
        this.participantsIds = participantsIds;
    }

    public void checkIfParticipateAvailable(LocalDateTime registerDate) {
        if (this.registerStartAt.isAfter(registerDate) || this.registerEndAt.isBefore(registerDate)) {
            throw new ApplicationException(ApiStatusType.FAIL_EVENT_NOT_REGISTER_PERIOD, "event not register period");
        }
    }

    public void checkIfFullOrThrowError() {
        if (this.participantsIds.size() >= maxParticipants) {
            throw new ApplicationException(ApiStatusType.FAIL_ALREADY_FULL_EVENT, "this event is already full");
        }
    }

    public void checkIfParticipatedOrThrowError(User user) {
        if (isAlreadyParticipated(user)) {
            throw new ApplicationException(ApiStatusType.FAIL_ALREADY_PARTICIPATED_USER, "this user is already participated");
        }
    }

    private boolean isAlreadyParticipated(User user) {
        return this.participantsIds.stream().anyMatch(
                participantsId -> participantsId.equals(user.getId()));
    }

}
