package com.banny.motd.domain.alarm;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Alarm {

    private Long id;
    private Long userId;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public Alarm(Long id, Long userId, AlarmType alarmType, AlarmArgs alarmArgs, LocalDateTime readAt, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.userId = userId;
        this.alarmType = alarmType;
        this.alarmArgs = alarmArgs;
        this.readAt = readAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

}
