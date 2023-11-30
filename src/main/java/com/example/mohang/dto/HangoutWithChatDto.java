package com.example.mohang.dto;

import com.example.mohang.domain.Hangout;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record HangoutWithChatDto(
        Long id,
        UserAccountDto userAccountDto,
        Set<ChatDto> chatDto,
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
) {

    public static HangoutWithChatDto of(Long id, UserAccountDto userAccountDto, Set<ChatDto> chatDto, String title, String content, String hashtag, LocalDateTime meetDate, String place, String address, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new HangoutWithChatDto(id, userAccountDto, chatDto, title, content, hashtag, meetDate, place, address, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static HangoutWithChatDto from(Hangout entity){
        return new HangoutWithChatDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getChats().stream()
                        .map(ChatDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
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

}
