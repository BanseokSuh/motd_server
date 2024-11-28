package com.banny.motd.domain.alarm.infrastructure;

import com.banny.motd.domain.alarm.Alarm;
import com.banny.motd.global.dto.request.SearchRequest;

import java.util.List;

public interface AlarmRepository {

    Alarm save(Alarm alarm);

    List<Alarm> getAlarmListBy(Long userId, SearchRequest request);

}
