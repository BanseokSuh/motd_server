package com.lightcc.motd.domain.reaction.infrastructure.entity;

import com.lightcc.motd.domain.reaction.domain.ReactionType;
import com.lightcc.motd.global.enums.TargetType;
import com.lightcc.motd.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@SQLRestriction("deleted_at IS NULL")
//@SQLDelete(sql = "UPDATE \"reaction\" SET deleted_at = NOW() where id = ?")
@Table(name = "\"reaction\"", uniqueConstraints = {
        @UniqueConstraint(name = "uk_reaction_user_id_target_type_target_id_reaction_type", columnNames = {"user_id", "target_type", "target_id", "reaction_type"})
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
        ReactionEntity entity = new ReactionEntity();
        entity.setUserId(userId);
        entity.setTargetType(targetType);
        entity.setTargetId(targetId);
        entity.setReactionType(reactionType);

        return entity;
    }
}
