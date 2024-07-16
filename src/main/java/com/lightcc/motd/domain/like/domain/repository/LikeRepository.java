package com.lightcc.motd.domain.like.domain.repository;

import com.lightcc.motd.domain.like.infrastructure.entity.LikeEntity;
import com.lightcc.motd.domain.post.infrastructure.entity.PostEntity;
import com.lightcc.motd.domain.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

    void deleteByUserAndPost(UserEntity user, PostEntity post);
}
