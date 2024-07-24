package com.banny.motd.domain.alarm.application;

import com.banny.motd.domain.alarm.domain.AlarmArgs;
import com.banny.motd.domain.alarm.domain.AlarmType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {

    void send(AlarmType alarmType, AlarmArgs alarmArgs, Long receiverUserId);

    SseEmitter subscribeAlarm(Long userId);
}
