package com.banny.motd.api.controller.event.response;

import com.banny.motd.domain.event.Event;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EventCreateResponse {

    private Long id;

    @Builder
    private EventCreateResponse(Long id) {
        this.id = id;
    }

    public static EventCreateResponse from(Event event) {
        return EventCreateResponse.builder().id(event.getId()).build();
    }

}
