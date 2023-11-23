package com.example.mohang.controller;

import com.example.mohang.domain.Hangout;
import com.example.mohang.dto.HangoutDto;
import com.example.mohang.entity.HangoutWith;
import com.example.mohang.repository.HangoutWithRepository;
import com.example.mohang.service.HangoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HangoutControllerTest {
    @Autowired
    private HangoutService service;
    @Autowired
    private HangoutWithRepository hangoutWithRepository;
    @Test
    void insert() {
        LocalDateTime date = LocalDateTime.of(2023,11,26,14,0,0);
        HangoutDto dto = new HangoutDto("테스트", "안녕하세요", "걍테스트", date, "엔젤리너스", "수원역", LocalDateTime.now(), "nohbin", LocalDateTime.now(), "nohbin");
        Hangout expected = new Hangout(dto);
        Hangout saved = service.write(dto);
        assertEquals(expected, saved);
    }

}
