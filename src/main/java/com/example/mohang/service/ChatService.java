package com.example.mohang.service;

import com.example.mohang.dto.ChatMessage;
import com.example.mohang.entity.Chat;
import com.example.mohang.repository.ChatRepository;
//import com.example.mohang.repository.HangOutRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j

public class ChatService {
//    @Autowired
//    private HangOutRepository hangOutRepository;
    @Autowired
    private ChatRepository chatRepository;
    public Chat insertChat(ChatMessage dto) {
//        HangOut hangout = hangOutRepository.findById(dto.getHang_id()).orElse(null);
//        Chat chat = Chat.createChat(dto, hangout);
        return null; //chatRepository.save(chat);
    }

    public List<ChatMessage> selectAllChat() {
        List<Chat> chatList = chatRepository.findAll();
        List<ChatMessage> dtoList = new ArrayList<>();
        for(Chat chat : chatList) {
            dtoList.add(ChatMessage.createChatMessage(chat));
        }
        return dtoList;
    }


    public List<ChatMessage> selectChatBySender(String sender) {
        List<Chat> chatList = chatRepository.findBySender(sender);
        List<ChatMessage> dtoList = new ArrayList<>();
        for(Chat chat : chatList) {
            dtoList.add(ChatMessage.createChatMessage(chat));
        }
        return dtoList;
    }

    public List<ChatMessage> selectChatByHangId(Long hangId) {
        List<Chat> chatList = chatRepository.findByHangId(hangId);
        List<ChatMessage> dtoList = new ArrayList<>();
        for(Chat chat : chatList) {
            dtoList.add(ChatMessage.createChatMessage(chat));
        }
        return dtoList;
    }
}
