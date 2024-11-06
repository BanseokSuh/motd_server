package com.banny.motd.api.service.event;

import com.banny.motd.api.service.event.request.EventCreateServiceRequest;
import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.event.EventType;
import com.banny.motd.domain.event.infrastructure.EventRepository;
import com.banny.motd.domain.participation.Participation;
import com.banny.motd.domain.participation.ParticipationStatus;
import com.banny.motd.domain.participation.infrastructure.ParticipationRepository;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import com.banny.motd.global.enums.TargetType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;

    @Transactional
    @Override
    public void createEvent(EventCreateServiceRequest request, Long userId) {
        User manager = userRepository.getById(userId);
        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .participationLimit(request.getParticipationLimit())
                .eventType(EventType.from(request.getEventType()))
                .manager(manager)
                .registerStartAt(request.getRegisterStartAt())
                .registerEndAt(request.getRegisterEndAt())
                .eventStartAt(request.getEventStartAt())
                .eventEndAt(request.getEventEndAt())
                .build();
        eventRepository.save(event);
    }

    @Transactional
    @Override
    public void participateEvent(Long eventId, Long userId, LocalDateTime applyDate) {
        /*
         * 1)
         * eventRepository에서 eventEntity를 조회할 때
         * event와 연관관계를 맺고 있는 participation 정보를 함께 조회
         *
         * 2) v
         * eventRepository에서는 eventEntity만 조회하고
         * participation은 participationRepository에서 별도로 조회하여 event.participation 필드에 할당
         *
         */
        Event event = eventRepository.getById(eventId);
        event.checkIfStartOrThrowError(applyDate);
        event.checkIfEndOrThrowError(applyDate);

        List<Long> participantsIds = participationRepository.getParticipantsIdBy(eventId);
        event.setParticipantsIds(participantsIds);
        event.checkIfFullOrThrowError();

        User user = userRepository.getById(userId);
        event.checkIfParticipatedOrThrowError(user);

        Participation participation = Participation.of(
                TargetType.EVENT,
                event.getId(),
                user,
                ParticipationStatus.PENDING);
        participationRepository.save(participation);
    }

}
