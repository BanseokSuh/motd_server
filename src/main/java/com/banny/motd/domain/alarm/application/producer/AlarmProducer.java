package com.banny.motd.domain.alarm.application.producer;

import com.banny.motd.domain.alarm.domain.event.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmProducer {

    private final KafkaTemplate<Long, AlarmEvent> kafkaTemplate;

    @Value("${spring.kafka.enabled}")
    private boolean kafkaEnabled;

    @Value("${spring.kafka.topic.alarm}")
    private String topic;

    public void send(AlarmEvent event) {
        if (!kafkaEnabled) {
            log.info("Kafka is disabled. Skip sending to kafka: {}", event);
            return;
        }

        kafkaTemplate.send(topic, event.getReceiverUserId(), event);

        log.info("Send to kafka finished: {}", event);
    }

}
