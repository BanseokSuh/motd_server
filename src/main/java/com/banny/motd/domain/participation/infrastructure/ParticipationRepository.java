package com.banny.motd.domain.participation.infrastructure;

import com.banny.motd.domain.participation.Participation;

import java.util.List;

public interface ParticipationRepository {

    void save(Participation participation);

    List<Long> getParticipantsIdBy(Long eventId);

}
