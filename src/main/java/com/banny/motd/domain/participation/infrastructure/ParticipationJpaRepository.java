package com.banny.motd.domain.participation.infrastructure;

import com.banny.motd.domain.participation.infrastructure.entity.ParticipationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationJpaRepository extends JpaRepository<ParticipationEntity, Long> {
}
