package com.banny.motd.domain.alarm;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {

    COMMENT("댓글", "%s님이 댓글을 달았습니다."),
    LIKE("좋아요", "%s님이 좋아요를 눌렀습니다.");

    private final String text;
    private final String message;

    public String generateMessage(String username) {
        return String.format(this.message, username);
    }

}
