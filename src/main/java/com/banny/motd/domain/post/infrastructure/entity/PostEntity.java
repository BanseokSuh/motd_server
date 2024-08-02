package com.banny.motd.domain.post.infrastructure.entity;

import com.banny.motd.domain.post.domain.Post;
import com.banny.motd.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Builder
@Getter
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE \"post\" SET deleted_at = NOW() where id = ?")
@Table(name = "\"post\"", indexes = {
        @Index(name = "index_post_user_id", columnList = "user_id"),
})
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

    public void setTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static PostEntity from(Post post) {
        return PostEntity.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUserId())
                .build();
    }

    public static PostEntity of(String title, String content, Long userId) {
        return PostEntity.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .build();

    }

    public Post toDomain() {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .userId(userId)
                .createdAt(getCreatedAt())
                .build();
    }
}
