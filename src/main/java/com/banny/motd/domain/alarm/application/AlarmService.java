package com.banny.motd.domain.alarm.application;

import com.banny.motd.domain.alarm.domain.Alarm;
import com.banny.motd.domain.alarm.domain.AlarmArgs;
import com.banny.motd.domain.alarm.domain.AlarmType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {

    Page<Alarm> getAlarmList(Long userId, Pageable pageable);

    SseEmitter subscribeAlarm(Long userId);

    void send(AlarmType alarmType, AlarmArgs alarmArgs, Long receiverUserId);
}
