package com.banny.motd.domain.reaction.infrastructure.entity;

import com.banny.motd.domain.reaction.domain.Reaction;
import com.banny.motd.domain.reaction.domain.ReactionType;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;
import com.banny.motd.global.entity.BaseEntity;
import com.banny.motd.global.enums.TargetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE \"reaction\" SET deleted_at = NOW() where id = ?")
@Table(name = "\"reaction\"", indexes = {
        @Index(name = "index_reaction_user_id_target_type_target_id_reaction_type", columnList = "user_id, target_type, target_id, reaction_type"),
})
@Entity
public class ReactionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "target_type", nullable = false, columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    @Column(name = "target_id", nullable = false, columnDefinition = "BIGINT")
    private Long targetId;

    @Column(name = "reaction_type", nullable = false, columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    public static ReactionEntity from(Reaction reaction) {
        return ReactionEntity.builder()
                .id(reaction.getId())
                .user(UserEntity.from(reaction.getAuthor()))
                .targetType(reaction.getTargetType())
                .targetId(reaction.getTargetId())
                .reactionType(reaction.getReactionType())
                .build();
    }

    public static ReactionEntity of(UserEntity userEntity, TargetType targetType, Long targetId, ReactionType reactionType) {
        return ReactionEntity.builder()
                .user(userEntity)
                .targetType(targetType)
                .targetId(targetId)
                .reactionType(reactionType)
                .build();
    }

    public Reaction toDomain() {
        return Reaction.builder()
                .id(id)
                .author(user.toDomain())
                .targetType(targetType)
                .targetId(targetId)
                .reactionType(reactionType)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .deletedAt(getDeletedAt())
                .build();
    }
}
