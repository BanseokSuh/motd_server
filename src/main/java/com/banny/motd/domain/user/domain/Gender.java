package com.banny.motd.domain.user.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Gender {

    MALE("남자"),
    FEMALE("여자"),
    ETC("기타");

    private final String text;

}
