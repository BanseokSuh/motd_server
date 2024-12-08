package com.banny.motd.domain.alarm;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlarmEvent {

    private Long receiverUserId;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;

    @Builder
    private AlarmEvent(Long receiverUserId, AlarmType alarmType, AlarmArgs alarmArgs) {
        this.receiverUserId = receiverUserId;
        this.alarmType = alarmType;
        this.alarmArgs = alarmArgs;
    }

    public static AlarmEvent from(Long receiverUserId, AlarmType alarmType, AlarmArgs alarmArgs) {
        return AlarmEvent.builder()
                .receiverUserId(receiverUserId)
                .alarmType(alarmType)
                .alarmArgs(alarmArgs)
                .build();
    }

}
