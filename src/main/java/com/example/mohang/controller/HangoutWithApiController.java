package com.example.mohang.controller;

import com.example.mohang.domain.Hangout;
import com.example.mohang.dto.HangoutDto;
import com.example.mohang.dto.HangoutWithDto;
import com.example.mohang.dto.UserAccountDto;
import com.example.mohang.entity.HangoutWith;
import com.example.mohang.repository.HangoutWithRepository;
import com.example.mohang.security.CustomPrincipal;
import com.example.mohang.service.HangoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/hangwith")
@RestController
@RequiredArgsConstructor
public class HangoutWithApiController {

    private final HangoutService hangoutService;
    @PostMapping("/participate")
    public void participate(@RequestBody HangoutWithDto dto, @AuthenticationPrincipal CustomPrincipal customPrincipal) {
        hangoutService.saveHangoutWith(dto, customPrincipal.toDto());
    }

}
