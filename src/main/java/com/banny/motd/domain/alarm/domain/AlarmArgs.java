package com.banny.motd.domain.alarm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmArgs implements Serializable {

    private Long fromUserId;
    private Long targetId;
}
