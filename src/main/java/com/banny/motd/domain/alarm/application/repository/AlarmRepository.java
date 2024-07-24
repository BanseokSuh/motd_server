package com.banny.motd.domain.alarm.application.repository;

import com.banny.motd.domain.alarm.infrastructure.entity.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {
}
