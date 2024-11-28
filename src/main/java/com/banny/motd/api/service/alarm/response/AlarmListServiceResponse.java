package com.banny.motd.api.service.alarm.response;

import com.banny.motd.domain.alarm.Alarm;
import com.banny.motd.domain.alarm.AlarmArgs;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlarmListServiceResponse {

    private Long id;
    private String message;
    private AlarmArgs alarmArgs;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;

    @Builder
    private AlarmListServiceResponse(Long id, String message, AlarmArgs alarmArgs, LocalDateTime readAt, LocalDateTime createdAt) {
        this.id = id;
        this.message = message;
        this.alarmArgs = alarmArgs;
        this.readAt = readAt;
        this.createdAt = createdAt;
    }

    public static AlarmListServiceResponse from(Alarm alarm, String message) {
        return AlarmListServiceResponse.builder()
                .id(alarm.getId())
                .message(message)
                .alarmArgs(alarm.getAlarmArgs())
                .readAt(alarm.getReadAt())
                .createdAt(alarm.getCreatedAt())
                .build();
    }

}
