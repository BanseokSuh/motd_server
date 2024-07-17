package com.lightcc.motd.domain.like.infrastructure.entity;

import com.lightcc.motd.domain.like.domain.Like;
import com.lightcc.motd.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE \"like\" SET deleted_at = NOW() where id = ?")
@Table(name = "\"like\"", indexes = {
        @Index(name = "like_user_id_post_id_idx", columnList = "user_id, post_id"),
        @Index(name = "like_post_id_idx", columnList = "post_id")
})
@Entity
public class LikeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

    public static LikeEntity of(Long userId, Long postId) {
        LikeEntity entity = new LikeEntity();
        entity.setUserId(userId);
        entity.setPostId(postId);

        return entity;
    }

    public Like toDomain() {
        Like like = new Like();
        like.setId(id);
        like.setUserId(userId);
        like.setPostId(postId);

        return like;
    }
}
