package com.example.mohang.dto;

import com.example.mohang.entity.HangoutWith;
import lombok.Data;

@Data
public class HangoutWithDto {
    private Long hangId;
    private String userId;
    private int writerOrNot;
    private String comment;

}
