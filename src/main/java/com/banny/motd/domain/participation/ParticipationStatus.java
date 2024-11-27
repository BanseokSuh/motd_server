package com.banny.motd.domain.participation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ParticipationStatus {

    PENDING("대기"),
    PARTICIPATED("참가"),
    CANCELED("취소");

    private final String text;

}
