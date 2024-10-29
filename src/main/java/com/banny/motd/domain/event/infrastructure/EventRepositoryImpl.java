package com.banny.motd.domain.event.infrastructure;

import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.event.infrastructure.entity.EventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository eventJpaRepository;

    @Override
    public Event save(Event event) {
        return eventJpaRepository.save(EventEntity.from(event)).toDomain();
    }

}
