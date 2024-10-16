package com.banny.motd.domain.event;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EventType {

    REGULAR("정기모임"),
    SPECIAL("특별모임");

    private final String text;

}
