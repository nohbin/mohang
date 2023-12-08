package com.example.mohang.domain;

import com.example.mohang.domain.constant.MessageType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
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
    @Getter
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private UserAccount userAccount;

    @Setter
    @Getter
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





}
