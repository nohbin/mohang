package com.example.mohang.dto;

import com.example.mohang.domain.Chat;
import com.example.mohang.domain.Hangout;
import com.example.mohang.domain.UserAccount;
import com.example.mohang.domain.constant.MessageType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ChatDto(
     Long id,
     @JsonProperty("hang_id")
     Long hang_id,
     MessageType type,
     UserAccountDto userAccountDto,
     String content,
     LocalDateTime createdAt
) {
    public static ChatDto of(
                          @JsonProperty("hang_id")
                          Long hang_id,
                          MessageType type,
                          UserAccountDto userAccountDto,
                          String content
                          ) {
        return new ChatDto(
                null,hang_id,type,userAccountDto,content,null
        );
    }

    public static ChatDto of(Long id,
                             @JsonProperty("hang_id")
                          Long hang_id,
                             MessageType type,
                             UserAccountDto userAccountDto,
                             String content,
                             LocalDateTime createdAt) {
        return new ChatDto(
                id,hang_id,type,userAccountDto,content,createdAt
        );
    }

    public static ChatDto from(Chat entity) {
       return new ChatDto(
               entity.getId(),
               entity.getHangout().getId(),
               entity.getType(),
               UserAccountDto.from(entity.getUserAccount()),
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
