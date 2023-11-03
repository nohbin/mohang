package com.example.mohang.entity;

import com.example.mohang.dto.HangOutDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="hangout")
public class HangOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private String writer;

    public static HangOut createHangOut(HangOutDto dto) {
        return new HangOut(
             dto.getId(), dto.getTitle(), dto.getContent(), dto.getWriter()
        );
    }
}
