package com.example.mohang.dto;

import com.example.mohang.entity.HangOut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HangOutDto {
    private Long id;

    private String title;

    private String content;

    private String writer;

    public static HangOutDto createHangOutDto(HangOut hangout) {
        return new HangOutDto(
                hangout.getId(), hangout.getTitle(), hangout.getContent(), hangout.getWriter()
        );
    }

}
