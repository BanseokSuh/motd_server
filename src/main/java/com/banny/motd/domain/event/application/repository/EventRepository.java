package com.banny.motd.domain.event.application.repository;

import com.banny.motd.domain.event.infrastructure.EventRepositoryCustom;
import com.banny.motd.domain.event.infrastructure.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long>, EventRepositoryCustom {
}
