package com.example.mohang.controller;

import com.example.mohang.domain.Hangout;
import com.example.mohang.dto.HangoutDto;
import com.example.mohang.entity.HangoutWith;
import com.example.mohang.repository.HangoutWithRepository;
import com.example.mohang.service.HangoutService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Disabled
@SpringBootTest
public class HangoutControllerTest {

    @Autowired
    private HangoutService service;

    @Autowired
    private HangoutWithRepository hangoutWithRepository;

//    @Disabled("에러 수정 전 테스트 사용 막음")
    @Test
    void insert() {


//        HangoutDto dto = new HangoutDto("테스트", "안녕하세요", "걍테스트", LocalDateTime.now(), "엔젤리너스", "수원역", null, null,null, null);
//        Hangout expected = new Hangout(dto);
//        Hangout saved = service.write(dto);
//        assertEquals(expected, saved);
    }

}
