package com.example.mohang.controller;

import com.example.mohang.domain.Hangout;
import com.example.mohang.dto.ChatMessage;
//import com.example.mohang.dto.HangOutDto;
//import com.example.mohang.repository.HangOutRepository;
import com.example.mohang.repository.HangoutRepository;
import com.example.mohang.service.ChatService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;
@Slf4j
@Controller
public class ChatController {
    @Autowired
    HangoutRepository hangoutRepository;
    @Autowired
    ChatService chatService;

    @Transactional
    @MessageMapping("/chat.sendMessage/{hang_id}")
    @SendTo("/room/{hang_id}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable String hang_id) {
        return chatMessage;
    }
    @Transactional
    @MessageMapping("/chat.addUser/{hang_id}")
    @SendTo("/room/{hang_id}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor,
                               @DestinationVariable String hang_id) {
        // Add username in web socket session
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", chatMessage.getSender());
        return chatMessage;
    }
    @GetMapping("/hangout/{hang_id}/chatting")
    public String chatting(Model model, @PathVariable Long hang_id) {
        Hangout hangout = hangoutRepository.findById(hang_id).orElse(null);
        model.addAttribute("hangout", hangout);
        List<ChatMessage> chatList = chatService.selectChatByHangId(hang_id);
        model.addAttribute("chatList", chatList);
        return "chatting/index";
    }
}
