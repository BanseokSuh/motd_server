package com.banny.motd.domain.comment.application.repository;

import com.banny.motd.domain.comment.infrastructure.CommentRepositoryCustom;
import com.banny.motd.domain.comment.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>, CommentRepositoryCustom {
}
