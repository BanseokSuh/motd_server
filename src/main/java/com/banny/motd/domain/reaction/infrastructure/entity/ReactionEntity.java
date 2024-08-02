package com.banny.motd.domain.reaction.infrastructure.entity;

import com.banny.motd.domain.reaction.domain.Reaction;
import com.banny.motd.domain.reaction.domain.ReactionType;
import com.banny.motd.global.entity.BaseEntity;
import com.banny.motd.global.enums.TargetType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Builder
@Getter
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE \"reaction\" SET deleted_at = NOW() where id = ?")
@Table(name = "\"reaction\"", indexes = {
        @Index(name = "index_reaction_user_id_target_type_target_id_reaction_type", columnList = "user_id, target_type, target_id, reaction_type"),
})
@Entity
public class ReactionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "target_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(name = "reaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    public static ReactionEntity of(Long userId, TargetType targetType, Long targetId, ReactionType reactionType) {
        return ReactionEntity.builder()
                .userId(userId)
                .targetType(targetType)
                .targetId(targetId)
                .reactionType(reactionType)
                .build();
    }

    public Reaction toDomain() {
        return Reaction.builder()
                .id(id)
                .userId(userId)
                .targetType(targetType)
                .targetId(targetId)
                .reactionType(reactionType)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .deletedAt(getDeletedAt())
                .build();
    }
}
