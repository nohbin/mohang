package com.example.mohang.controller;

import com.example.mohang.dto.HangoutWithDto;
import com.example.mohang.security.CustomPrincipal;
import com.example.mohang.service.HangoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/hangwith")
@RestController
@RequiredArgsConstructor
public class HangoutWithController {

    private final HangoutService hangoutService;
    @PostMapping("/participate")
    public void participate(@RequestBody HangoutWithDto dto, @AuthenticationPrincipal CustomPrincipal customPrincipal) {
        hangoutService.saveHangoutWith(dto, customPrincipal.toDto());
    }

}
