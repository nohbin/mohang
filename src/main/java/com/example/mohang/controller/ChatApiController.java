package com.example.mohang.controller;

import com.example.mohang.dto.ChatMessage;
import com.example.mohang.entity.Chat;
import com.example.mohang.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api")
@Slf4j
@RestController
public class ChatApiController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/hangout/chatting")
    public ResponseEntity<Chat> insertChat(@RequestBody ChatMessage dto) {
        Chat insertedChat = chatService.insertChat(dto);
        return ResponseEntity.status(HttpStatus.OK).body(insertedChat);
    }

    @GetMapping("/hangout/{hang_id}/chatting")
    public ResponseEntity<List<ChatMessage>> selectChatByHangId(@PathVariable Long hang_id) {
        List<ChatMessage> chatList = chatService.selectChatByHangId(hang_id);
        return ResponseEntity.status(HttpStatus.OK).body(chatList);
    }
}
