package com.lightcc.motd.domain.comment.infrastructure.entity;

import com.lightcc.motd.domain.comment.domain.Comment;
import com.lightcc.motd.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE \"comment\" SET deleted_at = NOW() where id = ?")
@Table(name = "\"comment\"")
@Entity
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "comment")
    private String comment;

    public static CommentEntity of(Long userId, Long postId, String comment) {
        CommentEntity entity = new CommentEntity();
        entity.setUserId(userId);
        entity.setPostId(postId);
        entity.setComment(comment);

        return entity;
    }

    public Comment toDomain() {
        Comment commment = new Comment();
        commment.setId(id);
        commment.setUserId(userId);
        commment.setPostId(postId);
        commment.setComment(comment);

        return commment;
    }
}
