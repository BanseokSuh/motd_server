package com.banny.motd.domain.comment.domain.repository;

import com.banny.motd.domain.comment.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByTargetId(Long targetId);
}
