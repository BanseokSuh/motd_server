package com.banny.motd.api.service.event;

import com.banny.motd.api.service.event.request.EventCreateServiceRequest;

import java.time.LocalDateTime;

public interface EventService {

    void createEvent(EventCreateServiceRequest request, Long userId);

    void participateEvent(Long eventId, Long userId, LocalDateTime applyDate);

}
