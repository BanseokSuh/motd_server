package com.banny.motd.api.service.event;

import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.event.EventType;

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
