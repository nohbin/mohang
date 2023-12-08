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
        String userId,
        String nickname,
        String email,
        String title,
        String content,
        String hashtag,
        LocalDateTime meetDate,
        String place,
        String address,
        String region1,
        String region2,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) implements Serializable {

    public static HangoutDto of(
                                String userId,
                                String nickname,
                                String email,
                                String title,
                                String content,
                                String hashtag,
                                LocalDateTime meetDate,
                                String place,
                                String address)
    {
        return new HangoutDto(null, userId, nickname, email, title, content, hashtag, meetDate, place, address,null,null,null,null,null,null);
    }

    public static HangoutDto of(
                                Long id,
                                String userId,
                                String nickname,
                                String email,
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
        return new HangoutDto(id, userId, nickname, email, title, content, hashtag, meetDate, place, address, address.split(" ")[0], address.split(" ")[1], createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static HangoutDto from(Hangout entity) {
        return new HangoutDto(
                entity.getId(),
                entity.getUserAccount().getUserId(),
                entity.getUserAccount().getNickname(),
                entity.getUserAccount().getEmail(),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getMeetDate(),
                entity.getPlace(),
                entity.getAddress(),
                entity.getRegion1(),
                entity.getRegion2(),
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