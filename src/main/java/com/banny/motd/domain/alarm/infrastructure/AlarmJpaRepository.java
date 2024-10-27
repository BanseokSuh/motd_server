package com.banny.motd.domain.alarm.infrastructure;

import com.banny.motd.domain.alarm.infrastructure.entity.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmJpaRepository extends JpaRepository<AlarmEntity, Long> {

}
