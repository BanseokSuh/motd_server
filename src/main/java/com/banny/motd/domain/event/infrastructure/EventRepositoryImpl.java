package com.banny.motd.domain.event.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository eventJpaRepository;

}
