package com.banny.motd.domain.event.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EnrollmentStatus {

    PENDING("대기"),
    ENROLLED("참가"),
    CANCEL("취소");

    private final String text;

}
