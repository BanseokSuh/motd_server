package com.banny.motd.domain.event.domain;

import com.banny.motd.domain.user.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Enrollment {

    private Long id;
    private Event event;
    private User user;
    private EnrollmentStatus enrollmentStatus;
    private LocalDateTime registerAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public void register() {
    }

}
