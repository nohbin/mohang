package com.example.mohang.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // SockJS연결 주소
                .withSockJS(); // 버전 낮은 브라우저에서도 적용 가능
    }   // 주 소 : localhost:8080/ws

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트에서 보낸 메시지를 받을 prefix
        registry.setApplicationDestinationPrefixes("/app");
        // 해당 주소를 구독하고 있는 클라이언트들에게 메시지 전달.
        registry.enableSimpleBroker("/room");
    }
}
