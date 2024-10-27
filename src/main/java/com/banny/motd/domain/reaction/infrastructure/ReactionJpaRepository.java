package com.banny.motd.domain.reaction.infrastructure;

import com.banny.motd.domain.reaction.infrastructure.entity.ReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionJpaRepository extends JpaRepository<ReactionEntity, Long> {
}
