package com.example.mohang.controller;

import com.example.mohang.domain.Hangout;
import com.example.mohang.dto.ChatDto;
//import com.example.mohang.dto.HangOutDto;
//import com.example.mohang.repository.HangOutRepository;
import com.example.mohang.repository.HangoutRepository;
import com.example.mohang.security.CustomPrincipal;
import com.example.mohang.service.ChatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;
@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final HangoutRepository hangoutRepository;
    private final ChatService chatService;

    @Transactional
    @MessageMapping("/chat.sendMessage/{hangoutId}")
    @SendTo("/room/{hangoutId}")
    public ChatDto sendMessage(@Payload ChatDto chatMessage, @DestinationVariable String hangoutId) {
        return chatMessage;
    }
    @Transactional
    @MessageMapping("/chat.addUser/{hangoutId}")
    @SendTo("/room/{hangoutId}")
    public ChatDto addUser(@Payload ChatDto chatMessage,
                           SimpMessageHeaderAccessor headerAccessor/*,
                           @DestinationVariable String hangoutId*/) {
        // Add username in web socket session
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("nickname", chatMessage.sender());
        return chatMessage;
    }
    @GetMapping("/hangout/{hangoutId}/chatting")
    public String chatting(Model model, @PathVariable Long hangoutId,
                                        @AuthenticationPrincipal CustomPrincipal customPrincipal) {
        Hangout hangout = hangoutRepository.findById(hangoutId).orElse(null);
        model.addAttribute("hangout", hangout);
        List<ChatDto> chatList = chatService.selectChatByHangId(hangoutId);
        model.addAttribute("chatList", chatList);
        model.addAttribute("sender",customPrincipal.nickname());
        return "chatting/index";
    }
}
