package com.banny.motd.domain.alarm.infrastructure.entity;

import com.banny.motd.domain.alarm.domain.Alarm;
import com.banny.motd.domain.alarm.domain.AlarmArgs;
import com.banny.motd.domain.alarm.domain.AlarmType;
import com.banny.motd.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Convert(attributeName = "jsonb", converter = AttributeConverter.class)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE \"alarm\" SET deleted_at = NOW() where id = ?")
@Table(name = "\"alarm\"", indexes = {
        @Index(name = "index_alarm_user_id_alarm_type", columnList = "user_id, alarm_type"),
})
@Entity
public class AlarmEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "user_id", nullable = false, columnDefinition = "BIGINT")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_type", nullable = false, columnDefinition = "VARCHAR(20)")
    private AlarmType alarmType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "alarm_args", nullable = false, columnDefinition = "jsonb")
    private AlarmArgs alarmArgs;

    public static AlarmEntity of(Long userId, AlarmType alarmType, AlarmArgs alarmArgs) {
        return AlarmEntity.builder()
                .userId(userId)
                .alarmType(alarmType)
                .alarmArgs(alarmArgs)
                .build();
    }

    public Alarm toDomain() {
        return Alarm.builder()
                .id(id)
                .userId(userId)
                .alarmType(alarmType)
                .alarmArgs(alarmArgs)
                .createdAt(getCreatedAt())
                .build();
    }

}
