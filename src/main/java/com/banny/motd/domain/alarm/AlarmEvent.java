package com.banny.motd.domain.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmEvent {

    private Long receiverUserId;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;

}
