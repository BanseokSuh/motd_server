package com.lightcc.motd.domain.post.infrastructure.entity;

import com.lightcc.motd.domain.post.domain.Post;
import com.lightcc.motd.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE \"post\" SET deleted_at = NOW() where id = ?")
@Table(name = "\"post\"")
@Entity
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public static PostEntity from(Post post) {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        postEntity.setUserId(post.getUserId());

        return postEntity;
    }

    public Post toDomain() {
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setContent(content);
        post.setUserId(userId);
        post.setCreatedAt(getCreatedAt());

        return post;
    }

    public static PostEntity of(String title, String content, Long userId) {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(title);
        postEntity.setContent(content);
        postEntity.setUserId(userId);

        return postEntity;
    }
}
