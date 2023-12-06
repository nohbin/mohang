package com.example.mohang.dto;

import com.example.mohang.domain.Chat;
import com.example.mohang.domain.Hangout;
import com.example.mohang.domain.UserAccount;
import com.example.mohang.domain.constant.MessageType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ChatDto(
     Long id,
     @JsonProperty("hangoutId")
     Long hangoutId,
     MessageType type,
     @JsonProperty("userId")
     String userId,
     String sender,
     String email,
     String content,
     LocalDateTime createdAt
) {
    public static ChatDto of(
                          @JsonProperty("hangoutId")
                          Long hangoutId,
                          MessageType type,
                          String userId,
                          String nickname,
                          String email,
                          String content
                          ) {
        return new ChatDto(
                null, hangoutId, type, userId, nickname, email, content,null
        );
    }

    public static ChatDto of(Long id,
                             @JsonProperty("hangoutId")
                             Long hangoutId,
                             MessageType type,
                             String userId,
                             String nickname,
                             String email,
                             String content,
                             LocalDateTime createdAt) {
        return new ChatDto(
                id,hangoutId,type,userId,nickname,email,content,createdAt
        );
    }

    public static ChatDto from(Chat entity) {
       return new ChatDto(
               entity.getId(),
               entity.getHangout().getId(),
               entity.getType(),
               entity.getUserAccount().getUserId(),
               entity.getUserAccount().getNickname(),
               entity.getUserAccount().getEmail(),
               entity.getContent(),
               entity.getCreatedAt()
       );
    }

    public Chat toEntity(Hangout entity, UserAccount userAccount){
        return Chat.of(
                entity,
                userAccount,
                content
        );
    }


//    public static ChatDto createChatMessage(Chat chat) {
//        return new ChatDto(
//                chat.getId(), chat.getType(), chat.getContent(), UserAccountDto.from(chat.getUserAccount()), chat.getHangout().getId(), chat.getCreatedAt().toString()
//        );
//    }
}
