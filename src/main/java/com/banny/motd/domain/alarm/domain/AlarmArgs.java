package com.banny.motd.domain.alarm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmArgs {

    private Long fromUserId;
    private Long targetId;
}
