package com.banny.motd.domain.participation.infrastructure.entity;

import com.banny.motd.domain.participation.Participation;
import com.banny.motd.domain.participation.ParticipationStatus;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;
import com.banny.motd.global.entity.BaseEntity;
import com.banny.motd.global.enums.TargetType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@NoArgsConstructor
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE \"participation\" SET deleted_at = now() WHERE id = ?")
@Table(name = "\"participation\"")
@Entity
public class ParticipationEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, columnDefinition = "VARCHAR(10)")
    private TargetType targetType;

    @Column(name = "target_id", nullable = false, columnDefinition = "BIGINT")
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "participation_status", nullable = false, columnDefinition = "VARCHAR(20)")
    private ParticipationStatus participationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Builder
    private ParticipationEntity(Long id, TargetType targetType, Long targetId, ParticipationStatus participationStatus, UserEntity user) {
        this.id = id;
        this.targetType = targetType;
        this.targetId = targetId;
        this.participationStatus = participationStatus;
        this.user = user;
    }

    public static ParticipationEntity from(Participation participation) {
        return ParticipationEntity.builder()
                .id(participation.getId())
                .targetType(participation.getTargetType())
                .targetId(participation.getTargetId())
                .user(UserEntity.from(participation.getUser()))
                .participationStatus(participation.getParticipationStatus())
                .build();
    }

    public static ParticipationEntity of(TargetType targetType, Long targetId, UserEntity user, ParticipationStatus participationStatus) {
        return ParticipationEntity.builder()
                .targetType(targetType)
                .targetId(targetId)
                .user(user)
                .participationStatus(participationStatus)
                .build();
    }

    public Participation toDomain() {
        return Participation.builder()
                .id(this.id)
                .targetType(this.targetType)
                .targetId(this.targetId)
                .user(this.user.toDomain())
                .participationStatus(this.participationStatus)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .deletedAt(getDeletedAt())
                .build();
    }

}
