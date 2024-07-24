package com.banny.motd.domain.alarm.application.consumer;

import com.banny.motd.domain.alarm.application.AlarmService;
import com.banny.motd.domain.alarm.application.helper.AlarmMessageHelper;
import com.banny.motd.domain.alarm.domain.event.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmConsumer {

    private final AlarmService alarmService;

    @KafkaListener(topics = "${spring.kafka.topic.alarm}")
    public void consumeAlarm(String message, Acknowledgment ack) {
        try {
            AlarmEvent event = AlarmMessageHelper.deserialize(message, AlarmEvent.class);

            alarmService.send(event.getAlarmType(), event.getAlarmArgs(), event.getReceiverUserId());

            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error deserializing message: {}", e.getMessage());
        }
    }
}
