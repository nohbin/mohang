package com.example.mohang.response;

import com.example.mohang.dto.HangoutWithChatDto;
import com.example.mohang.dto.UserAccountDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record HangoutWithChatResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname,
        LocalDateTime meetDate,
        String place,
        String address,
        Set<ChatResponse> chatResponse
) {

    public static HangoutWithChatResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname, LocalDateTime meetDate, String place, String address, Set<ChatResponse> chatResponse) {
       return new HangoutWithChatResponse(id, title, content, hashtag, createdAt, email, nickname, meetDate, place, address, chatResponse);
    }

    public static HangoutWithChatResponse from(HangoutWithChatDto dto){
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new HangoutWithChatResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname,
                dto.meetDate(),
                dto.place(),
                dto.address(),
                dto.chatDto().stream()
                        .map(ChatResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}
