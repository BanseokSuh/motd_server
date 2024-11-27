package com.banny.motd.api.service.event;

import com.banny.motd.api.service.event.request.EventCreateServiceRequest;
import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.event.EventDetail;
import com.banny.motd.domain.participation.Participation;
import com.banny.motd.global.dto.request.SearchRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<Event> getEventList(SearchRequest request);

    EventDetail getEvent(Long eventId, Long userId);

    Event createEvent(EventCreateServiceRequest request, Long userId);

    Participation participateEvent(Long eventId, Long userId, LocalDateTime participateDate);

    void cancelParticipateEvent(Long eventId, Long userId);

}
