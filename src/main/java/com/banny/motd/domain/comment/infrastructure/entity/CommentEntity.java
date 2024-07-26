package com.banny.motd.domain.comment.infrastructure.entity;

import com.banny.motd.domain.comment.domain.Comment;
import com.banny.motd.global.entity.BaseEntity;
import com.banny.motd.global.enums.TargetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE \"comment\" SET deleted_at = NOW() where id = ?")
@Table(name = "\"comment\"", indexes = {
        @Index(name = "index_comment_user_id_target_type_target_id", columnList = "user_id, target_type, target_id"),
})
@Entity
public class CommentEntity extends BaseEntity {

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

    @Column(name = "comment", columnDefinition = "TEXT", nullable = false)
    private String comment;

    public static CommentEntity of(Long userId, TargetType targetType, Long targetId, String comment) {
        CommentEntity entity = new CommentEntity();
        entity.setUserId(userId);
        entity.setTargetType(targetType);
        entity.setTargetId(targetId);
        entity.setComment(comment);

        return entity;
    }

    public Comment toDomain() {
        Comment commment = new Comment();
        commment.setId(id);
        commment.setUserId(userId);
        commment.setTargetType(targetType);
        commment.setTargetId(targetId);
        commment.setComment(comment);

        return commment;
    }
}
