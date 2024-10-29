package com.banny.motd.domain.event;

import com.banny.motd.domain.user.User;
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
    private int enrollmentLimit;
    private EventType eventType;
    private User manager;
    private LocalDateTime registerStartAt;
    private LocalDateTime registerEndAt;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public boolean isOpened() {
        return LocalDateTime.now().isAfter(registerStartAt) && LocalDateTime.now().isBefore(registerEndAt);
    }

    public boolean isExceeded(int currentEnrollmentCount) {
        return enrollmentLimit <= currentEnrollmentCount;
    }

    public boolean isRegistered(List<Enrollment> enrollments, Long userId) {
        return false;
    }

}
