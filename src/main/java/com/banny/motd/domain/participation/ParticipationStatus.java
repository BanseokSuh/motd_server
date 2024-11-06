package com.banny.motd.domain.participation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ParticipationStatus {

    PENDING("대기"),
    ENROLLED("참가"),
    CANCEL("취소");

    private final String text;

}
