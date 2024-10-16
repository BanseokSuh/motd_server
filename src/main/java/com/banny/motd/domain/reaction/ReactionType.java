package com.banny.motd.domain.reaction;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReactionType {

    LIKE("좋아요"),
    DISLIKE("싫어요");

    private final String text;

}
