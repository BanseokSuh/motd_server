package com.banny.motd.api.service.alarm;

import com.banny.motd.api.service.alarm.response.AlarmListServiceResponse;
import com.banny.motd.domain.alarm.AlarmArgs;
import com.banny.motd.domain.alarm.AlarmType;
import com.banny.motd.global.dto.request.SearchRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface AlarmService {

    List<AlarmListServiceResponse> getAlarmList(Long userId, SearchRequest request);

    SseEmitter subscribeAlarm(Long userId);

    void send(AlarmType alarmType, AlarmArgs alarmArgs, Long receiverUserId);

}
