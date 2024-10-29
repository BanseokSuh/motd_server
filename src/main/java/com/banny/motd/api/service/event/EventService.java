package com.banny.motd.api.service.event;

import com.banny.motd.api.service.event.request.EventCreateServiceRequest;

public interface EventService {

    void createEvent(EventCreateServiceRequest request, Long userId);

    void participateEvent(Long eventId, Long userId);

}
