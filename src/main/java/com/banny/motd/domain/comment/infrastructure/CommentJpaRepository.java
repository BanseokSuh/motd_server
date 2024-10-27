package com.banny.motd.domain.comment.infrastructure;

import com.banny.motd.domain.comment.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {

}
