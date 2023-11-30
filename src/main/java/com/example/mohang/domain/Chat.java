package com.example.mohang.domain;

import com.example.mohang.domain.constant.MessageType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@ToString
@Getter
@Entity
@Table(name="chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private MessageType type;

    @Column
    private String content;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private UserAccount userAccount;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    private Hangout hangout;

    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(updatable = false)
    private LocalDateTime createdAt;

    protected Chat(){}

    private Chat(Hangout hangout, UserAccount userAccount,String content){
        this.hangout = hangout;
        this.userAccount = userAccount;
        this.content = content;
    }

    public static Chat of(Hangout hangout, UserAccount userAccount,String content){
        return new Chat(hangout,userAccount,content);
    }

//    public static Chat createChat(ChatDto dto, Hangout hangout) {
//        // 예외 처리
//        if (hangout.getId() == null)
//            throw new IllegalArgumentException("채팅 전송 실패! 게시글의 id가 있어야 합니다.");
//        if (dto.getHang_id() != hangout.getId())
//            throw new IllegalArgumentException("채팅 전송 실패! 게시글의 id가 잘못되었습니다.");
//        // 엔티티 생성 및 반환
//        return new Chat(
//                dto.getId(),
//                dto.getType(),
//                dto.getContent(),
//                dto.getSender(),
//                hangout,
//                LocalDateTime.now()
//        );
//    }



}
