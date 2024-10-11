package com.banny.motd.domain.event.infrastructure.entity;

import com.banny.motd.domain.event.domain.Event;
import com.banny.motd.domain.event.domain.EventType;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;
import com.banny.motd.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Builder
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE \"post\" SET deleted_at = NOW() where id = ?")
@Table(name = "\"event\"")
@Entity
public class EventEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(50)")
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "enrollment_limit", nullable = false, columnDefinition = "INT")
    private int enrollmentLimit;

    @Column(name = "event_type", nullable = false, columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "register_start_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime registerStartAt;

    @Column(name = "register_end_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime registerEndAt;

    @Column(name = "event_start_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime eventStartAt;

    @Column(name = "event_end_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime eventEndAt;

    public static EventEntity of(String title, String description, int enrollmentLimit, EventType eventType, LocalDateTime registerStartAt, LocalDateTime registerEndAt, LocalDateTime eventStartAt, LocalDateTime eventEndAt, UserEntity userEntity) {
        return EventEntity.builder()
                .title(title)
                .description(description)
                .enrollmentLimit(enrollmentLimit)
                .eventType(eventType)
                .registerStartAt(registerStartAt)
                .registerEndAt(registerEndAt)
                .eventStartAt(eventStartAt)
                .eventEndAt(eventEndAt)
                .user(userEntity)
                .build();
    }

    public Event toDomain() {
        return Event.builder()
                .id(id)
                .title(title)
                .description(description)
                .enrollmentLimit(enrollmentLimit)
                .eventType(eventType)
                .manager(user.toDomain())
                .registerStartAt(registerStartAt)
                .registerEndAt(registerEndAt)
                .eventStartAt(eventStartAt)
                .eventEndAt(eventEndAt)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .deletedAt(getDeletedAt())
                .build();
    }

}
