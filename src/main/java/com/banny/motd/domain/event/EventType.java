package com.banny.motd.domain.event;

import com.banny.motd.global.dto.response.ApiResponseStatusType;
import com.banny.motd.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public enum EventType {

    REGULAR("정기모임"),
    SPECIAL("특별모임");

    private final String text;

    public static EventType from(String eventType) {

        log.info("eventType: {}", eventType);

        for (EventType e : EventType.values()) {
            if (e.name().equalsIgnoreCase(eventType)) {
                return e;
            }
        }

        throw new ApplicationException(ApiResponseStatusType.FAIL_INVALID_PARAMETER, "Invalid eventType");
    }
}
