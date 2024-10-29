package com.banny.motd.api.service.event;

import com.banny.motd.api.service.event.request.EventCreateServiceRequest;
import com.banny.motd.api.service.user.UserService;
import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.event.EventType;
import com.banny.motd.domain.event.infrastructure.EventRepository;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserService userService;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public void createEvent(EventCreateServiceRequest request, Long userId) {
        User manager = userRepository.getById(userId);
        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .enrollmentLimit(request.getEnrollmentLimit())
                .eventType(EventType.from(request.getEventType()))
                .manager(manager)
                .registerStartAt(request.getRegisterStartAt())
                .registerEndAt(request.getRegisterEndAt())
                .eventStartAt(request.getEventStartAt())
                .eventEndAt(request.getEventEndAt())
                .build();
        eventRepository.save(event);
    }

    @Override
    public void participateEvent(Long eventId, Long userId) {

    }

}
