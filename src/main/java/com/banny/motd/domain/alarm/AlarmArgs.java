package com.banny.motd.domain.alarm;

import com.banny.motd.global.enums.TargetType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlarmArgs {

    private Long fromUserId;
    private TargetType targetType;
    private Long targetId;

    @Builder
    private AlarmArgs(Long fromUserId, TargetType targetType, Long targetId) {
        this.fromUserId = fromUserId;
        this.targetType = targetType;
        this.targetId = targetId;
    }

    public static AlarmArgs of(Long fromUserId, TargetType targetType, Long targetId) {
        return AlarmArgs.builder()
                .fromUserId(fromUserId)
                .targetType(targetType)
                .targetId(targetId)
                .build();
    }

}
