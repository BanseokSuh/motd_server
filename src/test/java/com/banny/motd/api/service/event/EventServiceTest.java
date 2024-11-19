package com.banny.motd.api.service.event;

import com.banny.motd.api.service.event.request.EventCreateServiceRequest;
import com.banny.motd.domain.event.Event;
import com.banny.motd.domain.event.EventType;
import com.banny.motd.domain.event.infrastructure.EventRepository;
import com.banny.motd.domain.participation.Participation;
import com.banny.motd.domain.participation.ParticipationStatus;
import com.banny.motd.domain.participation.infrastructure.ParticipationRepository;
import com.banny.motd.global.enums.TargetType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@Sql(scripts = {"/sql/schema.sql"}, executionPhase = BEFORE_TEST_CLASS)
@Sql(scripts = {
        "/sql/init/createUsers.sql",
        "/sql/init/createEvents.sql"
}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"/sql/reset.sql"}, executionPhase = AFTER_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Test
    @DisplayName("이벤트를 등록한다.")
    void createEvent() {
        // given
        String title = "event title";
        String description = "event description";
        int maxParticipants = 30;
        String eventType = EventType.REGULAR.toString();
        LocalDateTime registerStartAt = LocalDateTime.of(2024, 11, 15, 0, 0);
        LocalDateTime registerEndAt = LocalDateTime.of(2024, 11, 16, 0, 0);
        LocalDateTime eventStartAt = LocalDateTime.of(2024, 12, 1, 9, 0);
        LocalDateTime eventEndAt = LocalDateTime.of(2024, 12, 1, 15, 0);

        EventCreateServiceRequest request = EventCreateServiceRequest.builder()
                .title(title)
                .description(description)
                .maxParticipants(maxParticipants)
                .eventType(eventType)
                .registerStartAt(registerStartAt)
                .registerEndAt(registerEndAt)
                .eventStartAt(eventStartAt)
                .eventEndAt(eventEndAt)
                .build();
        Long userId = 1L;

        // when
        Event event = eventService.createEvent(request, userId);

        // then
        assertThat(event)
                .extracting("id", "title", "description", "eventType", "maxParticipants")
                .contains(event.getId(), event.getTitle(), event.getDescription(), event.getEventType(), event.getMaxParticipants());
    }

    @Test
    @DisplayName("이벤트에 참여한다.")
    void participateEvent() {
        // given
        Long eventId = 1L;
        Long userId = 1L;
        LocalDateTime participateDate = LocalDateTime.of(2024, 11, 15, 12, 0);

        // when
        Participation participation = eventService.participateEvent(eventId, userId, participateDate);

        // then
        assertThat(participation)
                .extracting("id", "targetType", "targetId", "user.id", "participationStatus")
                .contains(1L, TargetType.EVENT, eventId, userId, ParticipationStatus.PENDING);
    }

    @Test
    @DisplayName("여러 명이 동시에 이벤트에 참여한다. - 예외 케이스")
    void participateEventMultipleUsersException() throws InterruptedException {
        // given
        Long eventId = 1L;
        Event event = eventRepository.getById(eventId);

        LocalDateTime participateDate = LocalDateTime.of(2024, 11, 15, 12, 0);

        int requestUserCount = 70; // 70명
        ExecutorService executorService = Executors.newFixedThreadPool(requestUserCount);
        CountDownLatch latch = new CountDownLatch(requestUserCount);

        // when
        for (int i = 1; i <= requestUserCount; i++) {
            final long userId = i;
            executorService.submit(() -> {
                try {
                    System.out.println(userId + "번째 스레드 접근 시작");
                    eventService.participateEvent(eventId, userId, participateDate);
                } finally {
                    latch.countDown();
                    System.out.println(userId + "번째 스레드 접근 종료");
                }
            });
        }

        latch.await(); // 모든 스레드 작업이 완료될 때까지 대기
        executorService.shutdown();

        // then
        List<Long> participantsIds = participationRepository.getParticipantsIdBy(eventId);
        assertThat(participantsIds.size()).isNotEqualTo(event.getMaxParticipants());
    }

    @Test
    @DisplayName("여러 명이 동시에 이벤트에 참여한다. - 해피 케이스")
    void participateEventMultipleUsersHappy() throws InterruptedException {
        // given
        Long eventId = 1L;
        Event event = eventRepository.getById(eventId);

        LocalDateTime participateDate = LocalDateTime.of(2024, 11, 15, 12, 0);

        int requestUserCount = 70; // 70명
        ExecutorService executorService = Executors.newFixedThreadPool(requestUserCount);
        CountDownLatch latch = new CountDownLatch(requestUserCount);

        // when
        for (int i = 1; i <= requestUserCount; i++) {
            final long userId = i;
            executorService.submit(() -> {
                try {
                    System.out.println(userId + "번째 스레드 접근 시작");
                    eventService.participateEvent(eventId, userId, participateDate);
                } finally {
                    latch.countDown();
                    System.out.println(userId + "번째 스레드 접근 종료");
                }
            });
        }

        latch.await(); // 모든 스레드 작업이 완료될 때까지 대기
        executorService.shutdown();

        // then
        List<Long> participantsIds = participationRepository.getParticipantsIdBy(eventId);
        assertThat(participantsIds.size()).isEqualTo(event.getMaxParticipants());
    }


}