package com.banny.motd.domain.alarm.infrastructure;

import com.banny.motd.domain.alarm.Alarm;

public interface AlarmRepository {

    Alarm save(Alarm alarm);

}
