package com.example.mohang.service;

import com.example.mohang.domain.Chat;
import com.example.mohang.domain.Hangout;
import com.example.mohang.domain.UserAccount;
import com.example.mohang.domain.constant.MessageType;
import com.example.mohang.dto.ChatDto;
import com.example.mohang.dto.UserAccountDto;
import com.example.mohang.repository.ChatRepository;
import com.example.mohang.repository.HangoutRepository;
import com.example.mohang.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("비즈니스 로직 - 채팅")
@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @InjectMocks
    private ChatService chatService;

    @Mock
    private HangoutRepository hangoutRepository;
    @Mock
    ChatRepository chatRepository;
    @Mock
    UserAccountRepository userAccountRepository;

    @DisplayName("게시글 ID 로 채팅방 을 조회한다")
    @Test
    void givenHangId_whenSearchingChatComments_thenReturnsChats(){
        Long hangId = 1L;
        Chat expected = createChat("content");
        given(chatRepository.findByHangout_Id(hangId)).willReturn(List.of(expected));

        List<ChatDto> actual = chatService.selectChatByHangId(hangId);

        assertThat(actual)
               .hasSize(1)
                .first().hasFieldOrPropertyWithValue("content", expected.getContent());
        BDDMockito.then(chatRepository).should().findByHangout_Id(hangId);
    }

    @DisplayName("채팅을 입력하면, 채팅을 저장한다")
    @Test
    void givenChatComment_whenSavingChatComment_thenSaveChatComment(){
        ChatDto dto = createChatDto("content");
        given(hangoutRepository.getReferenceById(dto.hang_id())).willReturn(createHangout());
        given(userAccountRepository.getReferenceById(dto.userAccountDto().userId())).willReturn(createUserAccount());
        given(chatRepository.save(ArgumentMatchers.any(Chat.class))).willReturn(null);

        chatService.saveChat(dto);

        BDDMockito.then(hangoutRepository).should().getReferenceById(dto.hang_id());
        BDDMockito.then(userAccountRepository).should().getReferenceById(dto.userAccountDto().userId());
        BDDMockito.then(chatRepository).should().save(ArgumentMatchers.any(Chat.class));
    }


    private UserAccount createUserAccount(){
        return UserAccount.of(
                "nohbin",
                "1234",
                "nohbin",
                "nohbin@email.com",
                null
        );
    }

    private Hangout createHangout(){
        return Hangout.of(
                createUserAccount(),
                "title",
                "content",
                "#drink",
                null,
                "place",
                "address"
        );
    }
    private Chat createChat(String content){
        return Chat.of(
                createHangout(),
                createUserAccount(),
                content
        );
    }

    private UserAccountDto userAccountDto(){
        return UserAccountDto.of(
                "nohbin",
                "1234",
                "nohbin@email.com",
                "nohbin",
                LocalDateTime.now(),
                "nohbin",
                LocalDateTime.now(),
                "nohbin"
        );
    }

    private ChatDto createChatDto(String content){
        return ChatDto.of(
                1L,
                1L,
                MessageType.CHAT,
                userAccountDto(),
                content,
                LocalDateTime.now()
        );
    }

}