package com.example.mohang.entity;

import com.example.mohang.dto.ChatMessage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private ChatMessage.MessageType type;
    @Column
    private String content;
    @Column
    private String sender;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hang_id")
    private HangOut hangout;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime time;

    public static Chat createChat(ChatMessage dto, HangOut hangout) {
        // 예외 처리
        if (hangout.getId() == null)
            throw new IllegalArgumentException("채팅 전송 실패! 게시글의 id가 있어야 합니다.");
        if (dto.getHang_id() != hangout.getId())
            throw new IllegalArgumentException("채팅 전송 실패! 게시글의 id가 잘못되었습니다.");
        // 엔티티 생성 및 반환
        return new Chat(
                dto.getId(),
                dto.getType(),
                dto.getContent(),
                dto.getSender(),
                hangout,
                LocalDateTime.now()
        );
    }

}
