package com.banny.motd.domain.alarm.infrastructure;

import com.banny.motd.domain.alarm.Alarm;
import com.banny.motd.domain.alarm.infrastructure.entity.AlarmEntity;
import com.banny.motd.global.dto.request.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class AlarmRepositoryImpl implements AlarmRepository {

    private final AlarmJpaRepository alarmJpaRepository;

    @Override
    public Alarm save(Alarm alarm) {
        return alarmJpaRepository.save(AlarmEntity.from(alarm)).toDomain();
    }

    @Override
    public List<Alarm> getAlarmListBy(Long userId, SearchRequest request) {
        return alarmJpaRepository.findByUserId(userId).stream()
                .map(AlarmEntity::toDomain)
                .collect(Collectors.toList());
    }

}
