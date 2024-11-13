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
    public AlarmEvent(Long receiverUserId, AlarmType alarmType, AlarmArgs alarmArgs) {
        this.receiverUserId = receiverUserId;
        this.alarmType = alarmType;
        this.alarmArgs = alarmArgs;
    }

}
