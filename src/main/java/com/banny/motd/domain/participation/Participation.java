package com.banny.motd.domain.participation;

import com.banny.motd.domain.user.User;
import com.banny.motd.global.enums.TargetType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Participation {

    private Long id;
    private TargetType targetType;
    private Long targetId;
    private User user;
    private ParticipationStatus participationStatus;
    private LocalDateTime registerAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    private Participation(Long id, TargetType targetType, Long targetId, User user, ParticipationStatus participationStatus, LocalDateTime registerAt, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.targetType = targetType;
        this.targetId = targetId;
        this.user = user;
        this.participationStatus = participationStatus;
        this.registerAt = registerAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Participation of(TargetType targetType, Long targetId, User user, ParticipationStatus participationStatus) {
        return Participation.builder()
                .targetType(targetType)
                .targetId(targetId)
                .user(user)
                .participationStatus(participationStatus)
                .registerAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
    }

}
