package com.banny.motd.domain.participation.infrastructure;

import com.banny.motd.domain.participation.Participation;
import com.banny.motd.domain.participation.ParticipationStatus;
import com.banny.motd.global.enums.TargetType;

import java.util.List;

public interface ParticipationRepository {

    Participation save(Participation participation);

    List<Long> getParticipantsIdBy(Long eventId);

    void deleteAllInBatch();

    ParticipationStatus getParticipationStatus(Long eventId, Long userId);

    void cancelParticipateEvent(Long eventId, Long userId, TargetType targetType);

}
