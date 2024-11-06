package com.banny.motd.domain.participation.infrastructure;

import com.banny.motd.domain.participation.Participation;
import com.banny.motd.domain.participation.infrastructure.entity.ParticipationEntity;
import com.banny.motd.domain.participation.infrastructure.entity.QParticipationEntity;
import com.banny.motd.global.enums.TargetType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ParticipationRepositoryImpl implements ParticipationRepository {

    private final ParticipationJpaRepository participationJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void save(Participation participation) {
        participationJpaRepository.save(ParticipationEntity.from(participation));
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

}
