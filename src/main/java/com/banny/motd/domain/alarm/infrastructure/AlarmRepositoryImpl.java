package com.banny.motd.domain.alarm.infrastructure;

import com.banny.motd.domain.alarm.Alarm;
import com.banny.motd.domain.alarm.infrastructure.entity.AlarmEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AlarmRepositoryImpl implements AlarmRepository {

    private final AlarmJpaRepository alarmJpaRepository;

    @Override
    public Alarm save(Alarm alarm) {
        return alarmJpaRepository.save(AlarmEntity.from(alarm)).toDomain();
    }

}
