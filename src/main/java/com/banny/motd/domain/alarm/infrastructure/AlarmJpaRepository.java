package com.banny.motd.domain.alarm.infrastructure;

import com.banny.motd.domain.alarm.infrastructure.entity.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmJpaRepository extends JpaRepository<AlarmEntity, Long> {

    List<AlarmEntity> findByUserId(Long userId);

}
