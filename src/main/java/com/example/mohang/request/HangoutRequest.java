package com.example.mohang.request;

import com.example.mohang.dto.HangoutDto;
import com.example.mohang.dto.UserAccountDto;

import java.time.LocalDateTime;

public record HangoutRequest(
        String title,
        String content,
        String hashtag,
        LocalDateTime meetdate,
        String place,
        String address
) {
    public static HangoutRequest of(String title,
                                    String content,
                                    String hashtag,
                                    LocalDateTime meetdate,
                                    String place,
                                    String address){
        return new HangoutRequest(title, content, hashtag, meetdate, place, address);
    }

    public HangoutDto toDto(UserAccountDto userAccountDto){
        return HangoutDto.of(
                userAccountDto,
                title,
                content,
                hashtag,
                meetdate,
                place,
                address
        );
    }
}
