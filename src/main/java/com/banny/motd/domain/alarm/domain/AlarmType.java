package com.banny.motd.domain.alarm.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {

    COMMENT("댓글"),
    LIKE("좋아요"),
    DISLIKE("싫어요"),
    FOLLOW("팔로우");

    private final String text;

}
