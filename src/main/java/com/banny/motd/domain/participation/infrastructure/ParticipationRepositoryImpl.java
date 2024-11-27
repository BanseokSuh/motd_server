package com.banny.motd.domain.participation.infrastructure;

import com.banny.motd.domain.participation.Participation;
import com.banny.motd.domain.participation.ParticipationStatus;
import com.banny.motd.domain.participation.infrastructure.entity.ParticipationEntity;
import com.banny.motd.domain.participation.infrastructure.entity.QParticipationEntity;
import com.banny.motd.global.enums.TargetType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ParticipationRepositoryImpl implements ParticipationRepository {

    private final ParticipationJpaRepository participationJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Participation save(Participation participation) {
        return participationJpaRepository.save(ParticipationEntity.from(participation)).toDomain();
    }

    @Override
    public List<Long> getParticipantsIdBy(Long eventId) {
        QParticipationEntity participation = QParticipationEntity.participationEntity;

        return jpaQueryFactory
                .select(participation.user.id)
                .from(participation)
                .where(participation.targetId.eq(eventId)
                        .and(participation.targetType.eq(TargetType.EVENT)))
                .fetch();
    }

    @Override
    public void deleteAllInBatch() {
        participationJpaRepository.deleteAllInBatch();
    }

    @Override
    public ParticipationStatus getParticipationStatus(Long eventId, Long userId) {
        QParticipationEntity participation = QParticipationEntity.participationEntity;

        return jpaQueryFactory
                .select(participation.participationStatus)
                .from(participation)
                .where(participation.targetId.eq(eventId)
                        .and(participation.user.id.eq(userId))
                        .and(participation.targetType.eq(TargetType.EVENT)))
                .fetchOne();
    }

    @Override
    public void cancelParticipateEvent(Long eventId, Long userId, TargetType targetType, ParticipationStatus participationStatus) {
        QParticipationEntity participation = QParticipationEntity.participationEntity;

        jpaQueryFactory.update(participation)
                .set(participation.deletedAt, LocalDateTime.now())
                .set(participation.participationStatus, participationStatus)
                .where(participation.targetId.eq(eventId)
                        .and(participation.user.id.eq(userId))
                        .and(participation.targetType.eq(targetType)))
                .execute();
    }

}
