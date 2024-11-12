package com.banny.motd.api.controller.event.response;

import com.banny.motd.domain.participation.Participation;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ParticipationCreateResponse {

    private Long id;

    @Builder
    private ParticipationCreateResponse(Long id) {
        this.id = id;
    }

    public static ParticipationCreateResponse from(Participation participation) {
        return ParticipationCreateResponse.builder().id(participation.getId()).build();
    }

}
