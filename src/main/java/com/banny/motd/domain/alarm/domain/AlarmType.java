package com.banny.motd.domain.alarm.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {
    COMMENT("New comment"),
    LIKE("New like"),
    DISLIKE("New dislike"),
    FOLLOW("New follow")
    ;

    private final String alarmText;
}
