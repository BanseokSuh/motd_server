package com.banny.motd.domain.event.application;

import com.banny.motd.domain.event.application.repository.EventRepository;
import com.banny.motd.domain.event.domain.Event;
import com.banny.motd.domain.event.domain.EventType;
import com.banny.motd.domain.event.infrastructure.entity.EventEntity;
import com.banny.motd.domain.user.application.UserService;
import com.banny.motd.domain.user.domain.User;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserService userService;
    private final EventRepository eventRepository;

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
