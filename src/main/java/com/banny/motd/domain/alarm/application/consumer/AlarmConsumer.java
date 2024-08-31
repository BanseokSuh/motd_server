package com.banny.motd.domain.alarm.application.consumer;

import com.banny.motd.domain.alarm.application.AlarmService;
import com.banny.motd.domain.alarm.domain.event.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmConsumer {

    private final AlarmService alarmService;

    @Value("${spring.kafka.enabled}")
    private boolean kafkaEnabled;

    @KafkaListener(topics = "${spring.kafka.topic.alarm}")
    public void consumeAlarm(AlarmEvent event, Acknowledgment ack) {
        if (!kafkaEnabled) {
            log.info("Kafka is disabled. Skip consuming from kafka: {}", event);
            ack.acknowledge();
            return;
        }

        try {
            alarmService.send(event.getAlarmType(), event.getAlarmArgs(), event.getReceiverUserId());

            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error deserializing message: {}", e.getMessage());
        }
    }

}
