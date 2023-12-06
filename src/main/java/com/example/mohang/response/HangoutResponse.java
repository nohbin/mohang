package com.example.mohang.response;

import com.example.mohang.dto.HangoutDto;

import java.time.LocalDateTime;

public record HangoutResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname,
        LocalDateTime meetDate,
        String place,
        String address
) {
    public static HangoutResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname, LocalDateTime meetDate, String place, String address) {
        return new HangoutResponse(id, title, content, hashtag, createdAt, email, nickname, meetDate, place, address);
    }

    public static HangoutResponse from(HangoutDto dto){
        String nickname = dto.nickname();
        if(nickname == null || nickname.isBlank()){
            nickname = dto.userId();
        }
        return new HangoutResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.email(),
                nickname,
                dto.meetDate(),
                dto.place(),
                dto.address()
        );
    }
}
