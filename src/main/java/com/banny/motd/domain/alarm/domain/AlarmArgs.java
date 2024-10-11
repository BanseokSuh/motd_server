package com.banny.motd.domain.alarm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmArgs {

    private Long fromUserId;
    private Long targetId;

}
