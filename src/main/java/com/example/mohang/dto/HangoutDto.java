package com.example.mohang.dto;

import com.example.mohang.domain.Hangout;
import com.example.mohang.domain.UserAccount;
import lombok.Builder;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.mohang.domain.Hangout}
 */
@Builder
public record HangoutDto(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        LocalDateTime meetDate,
        String place,
        String address,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) implements Serializable {

    public static HangoutDto of(
                                UserAccountDto userAccountDto,
                                String title,
                                String content,
                                String hashtag,
                                LocalDateTime meetDate,
                                String place,
                                String address)
    {
        return new HangoutDto(null,userAccountDto, title, content, hashtag, meetDate, place, address,null,null,null,null);
    }

    public static HangoutDto of(
                                Long id,
                                UserAccountDto userAccountDto,
                                String title,
                                String content,
                                String hashtag,
                                LocalDateTime meetDate,
                                String place,
                                String address,
                                LocalDateTime createdAt,
                                String createdBy,
                                LocalDateTime modifiedAt,
                                String modifiedBy)
    {
        return new HangoutDto(id, userAccountDto, title, content, hashtag, meetDate, place, address, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static HangoutDto from(Hangout entity) {
        return new HangoutDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getMeetDate(),
                entity.getPlace(),
                entity.getAddress(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public Hangout toEntity(UserAccount userAccount){
        return Hangout.of(
                userAccount,
                title,
                content,
                hashtag,
                meetDate,
                place,
                address
        );
    }
}