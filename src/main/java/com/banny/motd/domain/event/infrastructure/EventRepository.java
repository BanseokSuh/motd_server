package com.banny.motd.domain.event.infrastructure;

import com.banny.motd.domain.event.Event;

import java.util.Optional;

public interface EventRepository {

    Event save(Event event);

    Event getById(Long eventId);

    Optional<Event> findById(Long eventId);

}
