package com.example.mohang.controller;

import com.example.mohang.domain.constant.MessageType;
import com.example.mohang.dto.ChatDto;
import com.example.mohang.dto.UserAccountDto;
import com.example.mohang.security.CustomPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@Slf4j
@Component
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;


    public WebSocketEventListener(@Autowired SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomPrincipal) {
            String username = ((CustomPrincipal) authentication.getPrincipal()).getUsername();
            if (username != null) {
                log.info("User Disconnected: " + username);

//                ChatDto chatMessage = new ChatDto();
//                chatMessage.setType(MessageType.LEAVE);
//                chatMessage.setSender(username);

                Map<String, Object> chatMessage = new HashMap<>();
                chatMessage.put("type",MessageType.LEAVE);
                chatMessage.put("sender",username);

                messagingTemplate.convertAndSend("/room/public", chatMessage);
            }
        }
    }

}
