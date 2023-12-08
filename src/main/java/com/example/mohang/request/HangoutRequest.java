package com.example.mohang.request;

import com.example.mohang.dto.HangoutDto;
import com.example.mohang.dto.UserAccountDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record HangoutRequest(
        String title,
        String content,
        String hashtag,
        String meetDate,
        String place,
        String address
) {
    public static HangoutRequest of(String title,
                                    String content,
                                    String hashtag,
                                    String meetDate,
                                    String place,
                                    String address){
        return new HangoutRequest(title, content, hashtag, meetDate, place, address);
    }

    public HangoutDto toDto(UserAccountDto userAccountDto){
        return HangoutDto.of(
                userAccountDto.userId(),
                userAccountDto.nickname(),
                userAccountDto.email(),
                title,
                content,
                hashtag,
                LocalDateTime.parse(meetDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                place,
                address
        );
    }
}
