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
import com.banny.motd.global.exception.ApiStatusType;
import com.banny.motd.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;
    private final RedissonClient redissonClient;

    @Transactional
    @Override
    public Event createEvent(EventCreateServiceRequest request, Long userId) {
        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .maxParticipants(request.getMaxParticipants())
                .eventType(EventType.from(request.getEventType()))
                .userId(userId)
                .registerStartAt(request.getRegisterStartAt())
                .registerEndAt(request.getRegisterEndAt())
                .eventStartAt(request.getEventStartAt())
                .eventEndAt(request.getEventEndAt())
                .build();
        return eventRepository.save(event);
    }

    @Transactional
    @Override
    public Participation participateEvent(Long eventId, Long userId, LocalDateTime participateDate) {
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
        final String lockName = eventId.toString() + ":lock";
        final RLock lock = redissonClient.getLock(lockName);
        final String worker = Thread.currentThread().getName();

        try {
            if (!lock.tryLock(1, 3, TimeUnit.SECONDS)) {
                return null;
            }

            Event event = eventRepository.getById(eventId);

            event.isRegisterDateValid(participateDate);

            List<Long> participantsIds = participationRepository.getParticipantsIdBy(eventId);
            event.setParticipantsUserId(participantsIds);

            User user = userRepository.getById(userId);
            event.checkIfParticipatedOrThrowError(user);

            Participation participation = Participation.of(
                    TargetType.EVENT,
                    event.getId(),
                    user,
                    ParticipationStatus.PENDING);

            if (!event.isParticipantsFull()) {
                log.info("[{}] participation success!!", worker);
                return participationRepository.save(participation);
            } else {
                log.error("[{}] participation fail :(", worker);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }



        return null;
    }

}
