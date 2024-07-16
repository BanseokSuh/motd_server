package com.lightcc.motd.domain.like.infrastructure.entity;

import com.lightcc.motd.domain.post.infrastructure.entity.PostEntity;
import com.lightcc.motd.domain.user.infrastructure.entity.UserEntity;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    public static LikeEntity of(UserEntity userEntity, PostEntity postEntity) {
        LikeEntity entity = new LikeEntity();
        entity.setUser(userEntity);
        entity.setPost(postEntity);

        return entity;
    }
}
