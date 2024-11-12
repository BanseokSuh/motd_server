package com.banny.motd.api.service.event;

import com.banny.motd.api.service.event.request.EventCreateServiceRequest;
import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.participation.Participation;

import java.time.LocalDateTime;

public interface EventService {

    Event createEvent(EventCreateServiceRequest request, Long userId);

    Participation participateEvent(Long eventId, Long userId, LocalDateTime participateDate);

}
