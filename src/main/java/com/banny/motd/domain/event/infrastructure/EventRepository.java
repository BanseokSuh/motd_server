package com.banny.motd.domain.event.infrastructure;

import com.banny.motd.domain.event.Event;

public interface EventRepository {

    Event save(Event event);

}
