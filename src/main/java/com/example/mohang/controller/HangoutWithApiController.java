package com.example.mohang.controller;

import com.example.mohang.domain.Hangout;
import com.example.mohang.dto.HangoutDto;
import com.example.mohang.dto.HangoutWithDto;
import com.example.mohang.entity.HangoutWith;
import com.example.mohang.repository.HangoutWithRepository;
import com.example.mohang.service.HangoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/hangwith")
@RestController
public class HangoutWithApiController {
//    @Autowired
//    HangoutService hangoutService;
//    @Autowired
//    HangoutWithRepository hangoutWithRepository;
//    @PostMapping("/participate")
//    public ResponseEntity<HangoutWith> participate(@RequestBody HangoutWithDto dto) {
//        HangoutDto hangout = hangoutService.getHangout(dto.getHangId());
//        HangoutWith hangoutWith = new HangoutWith(hangout, dto.getUserId(), dto.getWriterOrNot());
//        return ResponseEntity.ok().body(hangoutWithRepository.save(hangoutWith));
//    }

}
