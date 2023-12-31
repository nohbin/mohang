package com.example.mohang.domain.constant;

import lombok.Getter;

public enum SearchType {
    TITLE("제목"),
    CONTENT("본문"),
    NICKNAME("닉네임"),
    HASHTAG("해시태그"),
    REGION("지역");

    @Getter
    private final String description;

    SearchType(String description) {
        this.description = description;
    }
}
