package com.banny.motd.domain.alarm.domain;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AlarmType {
    COMMENT("New comment!"),
    LIKE("New like!"),
    FOLLOW("New follow!")
    ;

    private final String alarmText;
}
