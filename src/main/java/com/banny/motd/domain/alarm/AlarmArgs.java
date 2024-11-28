package com.banny.motd.domain.alarm;

import com.banny.motd.global.enums.TargetType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlarmArgs {

    private Long fromUserId;
    private Long targetId;
    private TargetType targetType;

    @Builder
    public AlarmArgs(Long fromUserId, Long targetId, TargetType targetType) {
        this.fromUserId = fromUserId;
        this.targetId = targetId;
        this.targetType = targetType;
    }

}
