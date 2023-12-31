package com.example.mohang.entity;

import com.example.mohang.domain.Hangout;
import com.example.mohang.dto.HangoutWithDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name="hangout_with")
@IdClass(HangoutWithID.class)
public class HangoutWith {
    @Id
    @ManyToOne
    private Hangout hangout;

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "writer_or_not")
    private int writerOrNot;
    @Column
    private String comment;

    public HangoutWith(Hangout hangout, String userId, int won) {
        this.hangout = hangout;
        this.userId = userId;
        this.writerOrNot = won;
    }

    public static HangoutWith of(Hangout hangout, String userId, int writerOrNot) {
        return new HangoutWith(hangout, userId, writerOrNot);
    }


}
