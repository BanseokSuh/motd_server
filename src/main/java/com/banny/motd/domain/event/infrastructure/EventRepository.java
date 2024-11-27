package com.banny.motd.domain.event.infrastructure;

import com.banny.motd.domain.event.Event;
import com.banny.motd.global.dto.request.SearchRequest;

import java.util.List;
import java.util.Optional;

public interface EventRepository {

    List<Event> getEventList(SearchRequest request);

    Event save(Event event);

    Event getById(Long eventId);

    Optional<Event> findById(Long eventId);

}
