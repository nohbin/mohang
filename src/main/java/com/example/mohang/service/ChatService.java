package com.example.mohang.service;

import com.example.mohang.domain.Hangout;
import com.example.mohang.domain.UserAccount;
import com.example.mohang.dto.ChatDto;
import com.example.mohang.domain.Chat;
import com.example.mohang.repository.ChatRepository;
//import com.example.mohang.repository.HangOutRepository;
import com.example.mohang.repository.HangoutRepository;
import com.example.mohang.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatService {
    private final ChatRepository chatRepository;
    private final HangoutRepository hangoutRepository;
    private final UserAccountRepository userAccountRepository;

//    public Chat insertChat(ChatDto dto) {
//       HangOut hangout = hangOutRepository.findById(dto.getHang_id()).orElse(null);
//       Chat chat = Chat.createChat(dto, hangout);
//        return null; //chatRepository.save(chat);
//    }

//    public List<ChatDto> selectAllChat() {
//        List<Chat> chatList = chatRepository.findAll();
//        List<ChatDto> dtoList = new ArrayList<>();
//        for(Chat chat : chatList) {
//            dtoList.add(ChatDto.createChatMessage(chat));
//        }
//        return dtoList;
//    }
//
//
//    public List<ChatDto> selectChatBySender(String sender) {
//        List<Chat> chatList = chatRepository.findBySender(sender);
//        List<ChatDto> dtoList = new ArrayList<>();
//        for(Chat chat : chatList) {
//            dtoList.add(ChatDto.createChatMessage(chat));
//        }
//        return dtoList;
//    }
//
    public List<ChatDto> selectChatByHangId(Long hangId) {
//        List<Chat> chatList = chatRepository.findByHangId(hangId);
//        List<ChatDto> dtoList = new ArrayList<>();
//        for(Chat chat : chatList) {
//            dtoList.add(ChatDto.createChatMessage(chat));
//        }
        return chatRepository.findByHangout_Id(hangId)
                .stream()
                .map(ChatDto::from)
                .toList();
    }

    public void saveChat(ChatDto dto) {
        Hangout hangout = hangoutRepository.getReferenceById(dto.hang_id());
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
        chatRepository.save(dto.toEntity(hangout,userAccount));
    }
}
