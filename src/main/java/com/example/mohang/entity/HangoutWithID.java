package com.example.mohang.entity;

import com.example.mohang.domain.Hangout;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HangoutWithID implements Serializable {
    private Hangout hangout;
    private String userId;
}
