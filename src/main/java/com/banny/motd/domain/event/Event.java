package com.banny.motd.domain.event;

import com.banny.motd.domain.user.User;
import com.banny.motd.global.exception.ApiResponseStatusType;
import com.banny.motd.global.exception.ApplicationException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class Event {

    private Long id;
    private String title;
    private String description;
    private EventType eventType;
    private User manager;
    private int participationLimit;
    private List<Long> participantsIds;
    private LocalDateTime registerStartAt;
    private LocalDateTime registerEndAt;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public void setParticipantsIds(List<Long> participantsIds) {
        this.participantsIds = participantsIds;
    }

    public void checkIfStartOrThrowError(LocalDateTime applyDate) {
        if (this.eventStartAt.isAfter(applyDate)) {
            throw new ApplicationException(ApiResponseStatusType.FAIL_EVENT_NOT_STARTED, "the event has not been started");
        }
    }

    public void checkIfEndOrThrowError(LocalDateTime applyDate) {
        if (this.eventEndAt.isBefore(applyDate)) {
            throw new ApplicationException(ApiResponseStatusType.FAIL_EVENT_FINISHED, "the event is already finished");
        }
    }

    public void checkIfFullOrThrowError() {
        if (this.participantsIds.size() >= participationLimit) {
            throw new ApplicationException(ApiResponseStatusType.FAIL_ALREADY_FULL_EVENT, "this event is already full");
        }
    }

    public void checkIfParticipatedOrThrowError(User user) {
        if (isAlreadyParticipated(user)) {
            throw new ApplicationException(ApiResponseStatusType.FAIL_ALREADY_PARTICIPATED_USER, "this user is already participated");
        }
    }

    private boolean isAlreadyParticipated(User user) {
        return this.participantsIds.stream().anyMatch(
                participantsId -> participantsId.equals(user.getId()));
    }
}
