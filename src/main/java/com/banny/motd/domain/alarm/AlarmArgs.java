package com.banny.motd.domain.alarm;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AlarmArgs {

    private Long fromUserId;
    private Long targetId;

    @Builder
    public AlarmArgs(Long fromUserId, Long targetId) {
        this.fromUserId = fromUserId;
        this.targetId = targetId;
    }

}
