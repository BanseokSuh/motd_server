package com.banny.motd.domain.post.infrastructure.entity;

import com.banny.motd.domain.post.Post;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;
import com.banny.motd.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE \"post\" SET deleted_at = NOW() where id = ?")
@Table(name = "\"post\"", indexes = {
        @Index(name = "index_post_user_id", columnList = "user_id"),
})
@Entity
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "image_urls", nullable = false, columnDefinition = "VARCHAR(255) ARRAY")
    private List<String> imageUrls;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public static PostEntity from(Post post) {
        return PostEntity.builder()
                .id(post.getId())
                .imageUrls(post.getImageUrls())
                .content(post.getContent())
                .user(UserEntity.from(post.getAuthor()))
                .build();
    }

    public static PostEntity of(List<String> imageUrls, String content, UserEntity userEntity) {
        return PostEntity.builder()
                .imageUrls(imageUrls)
                .content(content)
                .user(userEntity)
                .build();
    }

    public Post toDomain() {
        return Post.builder()
                .id(id)
                .imageUrls(imageUrls)
                .content(content)
                .author(user.toDomain())
                .createdAt(getCreatedAt())
                .build();
    }

}


