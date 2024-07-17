package com.lightcc.motd.domain.comment.domain.repository;

import com.lightcc.motd.domain.comment.infrastructure.entity.CommentEntity;
import com.lightcc.motd.domain.post.infrastructure.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPost(PostEntity post);
}
