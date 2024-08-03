package com.banny.motd.domain.reaction.application.repository;

import com.banny.motd.domain.reaction.infrastructure.ReactionRepositoryCustom;
import com.banny.motd.domain.reaction.infrastructure.entity.ReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<ReactionEntity, Long>, ReactionRepositoryCustom {
}
