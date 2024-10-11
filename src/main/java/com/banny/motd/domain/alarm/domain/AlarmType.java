package com.banny.motd.domain.alarm.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {

    COMMENT("댓글이 달렸습니다."),
    LIKE("좋아요가 눌렸습니다."),
    DISLIKE("싫어요가 눌렸습니다."),
    FOLLOW("팔로우가 눌렸습니다."),
    ;

    private final String alarmText;

}
