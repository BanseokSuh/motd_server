package com.banny.motd.domain.user.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatus {
    
    PENDING("대기"),
    ACTIVE("활성"),
    INACTIVE("비활성"),
    DELETED("삭제");

    private final String text;

}
