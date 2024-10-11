package com.banny.motd.domain.event.application;

import com.banny.motd.domain.event.domain.Event;
import com.banny.motd.domain.event.domain.EventType;

import java.time.LocalDateTime;

public interface EventService {

    Event createEvent(
            String title,
            String description,
            int enrollmentLimit,
            EventType eventType,
            LocalDateTime registerStartAt,
            LocalDateTime registerEndAt,
            LocalDateTime eventStartAt,
            LocalDateTime eventEndAt,
            Long userId);

    void participateEvent(Long eventId, Long userId);

}
