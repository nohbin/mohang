package com.example.mohang.dto;

import com.example.mohang.domain.Hangout;
import com.example.mohang.domain.UserAccount;
import com.example.mohang.entity.HangoutWith;
import com.example.mohang.request.HangoutRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.mohang.entity.HangoutWith}
 */
public record HangoutWithDto(
        Long hangoutId,
        String userId,
        int writerOrNot,
        String comment
) {
    public static HangoutWithDto of(Long hangoutId, String userId, int writerOrNot) {
        return new HangoutWithDto(hangoutId, userId, writerOrNot, null);
    }
    public static HangoutWithDto of(Long hangoutId, String userId, int writerOrNot, String comment) {
        return new HangoutWithDto(hangoutId, userId, writerOrNot, comment);
    }

    public static HangoutWithDto from(HangoutWith entity){
        return new HangoutWithDto(
                entity.getHangout().getId(),
                entity.getUserId(),
                entity.getWriterOrNot(),
                entity.getComment()
        );
    }

    public HangoutWith toEntity(Hangout hangout){
        return HangoutWith.of(
                hangout,
                userId,
                writerOrNot
        );
    }


}
