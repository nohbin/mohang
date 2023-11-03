package com.example.mohang.dto;

import com.example.mohang.entity.Chat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessage {
    private Long id;
    private MessageType type;
    private String content;
    private String sender;
    @JsonProperty("hang_id")
    private Long hang_id;
    private String time;


    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public static ChatMessage createChatMessage(Chat chat) {
        return new ChatMessage(
                chat.getId(), chat.getType(), chat.getContent(), chat.getSender(), chat.getHangout().getId(), chat.getTime().toString()
        );
    }
}
