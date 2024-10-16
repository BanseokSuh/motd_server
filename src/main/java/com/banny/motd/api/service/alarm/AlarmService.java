package com.banny.motd.api.service.alarm;

import com.banny.motd.domain.alarm.Alarm;
import com.banny.motd.domain.alarm.AlarmArgs;
import com.banny.motd.domain.alarm.AlarmType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {

    Page<Alarm> getAlarmList(Long userId, Pageable pageable);

    SseEmitter subscribeAlarm(Long userId);

    void send(AlarmType alarmType, AlarmArgs alarmArgs, Long receiverUserId);
    
}
