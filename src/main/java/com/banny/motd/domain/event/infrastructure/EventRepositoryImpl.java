package com.banny.motd.domain.event.infrastructure;

import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.event.infrastructure.entity.EventEntity;
import com.banny.motd.domain.event.infrastructure.entity.QEventEntity;
import com.banny.motd.global.dto.request.SearchRequest;
import com.banny.motd.global.exception.ApiStatusType;
import com.banny.motd.global.exception.ApplicationException;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository eventJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Event> getEventList(SearchRequest request) {
        QEventEntity event = QEventEntity.eventEntity;
        return jpaQueryFactory
                .selectFrom(event)
                .orderBy(new OrderSpecifier<>(Order.DESC, event.createdAt))
                .limit(request.getSize())
                .offset(request.getOffset())
                .stream().map(EventEntity::toDomain)
                .toList();
    }

    @Override
    public Event save(Event event) {
        return eventJpaRepository.save(EventEntity.from(event)).toDomain();
    }

    @Override
    public Event getById(Long eventId) {
        return findById(eventId).orElseThrow(() -> new ApplicationException(ApiStatusType.FAIL_EVENT_NOT_FOUND, "The event is not found"));
    }

    @Override
    public Optional<Event> findById(Long eventId) {
        return eventJpaRepository.findById(eventId).map(EventEntity::toDomain);
    }

}
