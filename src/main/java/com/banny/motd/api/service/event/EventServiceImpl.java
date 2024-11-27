package com.banny.motd.api.service.event;

import com.banny.motd.api.service.event.request.EventCreateServiceRequest;
import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.event.EventDetail;
import com.banny.motd.domain.event.EventType;
import com.banny.motd.domain.event.infrastructure.EventRepository;
import com.banny.motd.domain.participation.Participation;
import com.banny.motd.domain.participation.ParticipationStatus;
import com.banny.motd.domain.participation.infrastructure.ParticipationRepository;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import com.banny.motd.global.annotation.DistributedLock;
import com.banny.motd.global.dto.request.SearchRequest;
import com.banny.motd.global.enums.TargetType;
import com.banny.motd.global.exception.ApiStatusType;
import com.banny.motd.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
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
    private final RedissonClient redissonClient;

    @Override
    public List<Event> getEventList(SearchRequest request) {
        return eventRepository.getEventList(request);
    }

    @Override
    public EventDetail getEvent(Long eventId) {
        Event event = eventRepository.getById(eventId);
        User user = userRepository.getById(event.getRegisterUserId());

        return new EventDetail(event, user);
    }

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

    @DistributedLock(key = "#eventId")
    @Transactional
    @Override
    public Participation participateEvent(Long eventId, Long userId, LocalDateTime participateDate) {
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
            log.info("participation success!!");
            return participationRepository.save(participation);
        } else {
            log.error("participation fail :(");
            throw new ApplicationException(ApiStatusType.FAIL_EVENT_FULL);
        }
    }

//    @Transactional
//    @Override
//    public Participation participateEvent(Long eventId, Long userId, LocalDateTime participateDate) {
//        final String lockName = "RLock:EVENT:" + eventId.toString();
//        final RLock lock = redissonClient.getLock(lockName);
//        long waitTime = 5L;
//        long leaseTime = 3L;
//
//        try {
//            boolean available = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
//
//            if (!available) {
//                log.info("락 획득하지 못함 :(");
//                throw new ApplicationException(ApiStatusType.FAIL_ENABLE_ACQUIRE_LOCK);
//            } else {
//                log.info("락 획득 :)");
//            }
//
//            Event event = eventRepository.getById(eventId);
//            event.isRegisterDateValid(participateDate);
//
//            List<Long> participantsIds = participationRepository.getParticipantsIdBy(eventId);
//            event.setParticipantsUserId(participantsIds);
//
//            User user = userRepository.getById(userId);
//            event.checkIfParticipatedOrThrowError(user);
//
//            Participation participation = Participation.of(
//                    TargetType.EVENT,
//                    event.getId(),
//                    user,
//                    ParticipationStatus.PENDING);
//
//            if (!event.isParticipantsFull()) {
//                log.info("이벤트 참여 성공!!");
//                return participationRepository.save(participation);
//            } else {
//                log.error("이벤트 참여 실패 :(");
//                throw new ApplicationException(ApiStatusType.FAIL_EVENT_FULL);
//            }
//        } catch (InterruptedException e) {
//            log.info("InterruptedException :(");
//            throw new ApplicationException(ApiStatusType.FAIL_SERVER_ERROR, e.toString());
//        } finally {
//            log.info("락 해제 !! \n");
//            if (lock != null && lock.isLocked()) {
//                lock.unlock();
//            }
//        }
//    }

}
