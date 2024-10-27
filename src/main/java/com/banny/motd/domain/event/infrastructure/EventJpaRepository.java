package com.banny.motd.domain.event.infrastructure;

import com.banny.motd.domain.event.infrastructure.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<EventEntity, Long>  {
}
