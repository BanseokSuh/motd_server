package com.banny.motd.domain.alarm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Alarm {

    private Long id;
    private Long userId;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
}
