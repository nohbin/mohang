package com.example.mohang.dto;

import com.example.mohang.entity.HangoutWith;
import com.example.mohang.request.HangoutRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HangoutWithDto {

    private Long hangId;
    private String userId;
    private int writerOrNot;
    private String comment;
    private UserAccountDto userAccountDto;


}
