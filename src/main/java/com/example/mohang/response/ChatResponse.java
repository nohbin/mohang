package com.example.mohang.response;

import com.example.mohang.dto.ChatDto;

import java.time.LocalDateTime;

public record ChatResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname,
        String userId
        ) {
    public static ChatResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId) {
        return new ChatResponse(id, content, createdAt, email, nickname, userId);
    }

    public static ChatResponse from(ChatDto dto){
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }
        return new ChatResponse(
                dto.id(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname,
                dto.userAccountDto().userId()
        );
    }
}
