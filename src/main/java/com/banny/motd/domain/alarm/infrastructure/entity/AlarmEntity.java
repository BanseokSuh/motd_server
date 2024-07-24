package com.banny.motd.domain.alarm.infrastructure.entity;

import com.banny.motd.domain.alarm.domain.AlarmArgs;
import com.banny.motd.domain.alarm.domain.AlarmType;
import com.banny.motd.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Convert(attributeName = "jsonb", converter = AttributeConverter.class)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE \"alarm\" SET deleted_at = NOW() where id = ?")
@Table(name = "\"alarm\"")
@Entity
public class AlarmEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_type", nullable = false)
    private AlarmType alarmType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "alarm_args", columnDefinition = "jsonb", nullable = false)
    private AlarmArgs alarmArgs;

    public static AlarmEntity of(Long userId, AlarmType alarmType, AlarmArgs alarmArgs) {
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity.userId = userId;
        alarmEntity.alarmType = alarmType;
        alarmEntity.alarmArgs = alarmArgs;
        return alarmEntity;
    }
}
