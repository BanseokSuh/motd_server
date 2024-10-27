package com.banny.motd.api.service.event;

import com.banny.motd.domain.event.infrastructure.EventJpaRepository;
import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.event.EventType;
import com.banny.motd.domain.event.infrastructure.entity.EventEntity;
import com.banny.motd.api.service.user.UserService;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.infrastructure.eneity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserService userService;
    private final EventJpaRepository eventRepository;

    @Override
    public Event createEvent(
            String title,
            String description,
            int enrollmentLimit,
            EventType eventType,
            LocalDateTime registerStartAt,
            LocalDateTime registerEndAt,
            LocalDateTime eventStartAt,
            LocalDateTime eventEndAt,
            Long userId) {

        User user = userService.getUserOrException(userId);

        return eventRepository.save(EventEntity.of(
                title,
                description,
                enrollmentLimit,
                eventType,
                registerStartAt,
                registerEndAt,
                eventStartAt,
                eventEndAt,
                UserEntity.from(user)
        )).toDomain();
    }

    @Override
    public void participateEvent(Long eventId, Long userId) {

    }

}
