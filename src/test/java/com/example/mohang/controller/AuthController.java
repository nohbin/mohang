package com.example.mohang.controller;

import com.example.mohang.config.SecurityConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled("오류 수정 예정")
@DisplayName("VIEW 컨트롤러 인증 테스트")
@WebMvcTest
@Import(SecurityConfig.class)
public class AuthController {

    private final MockMvc mvc;



    public AuthController(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }


    @Test
    @DisplayName("[view][Get] - 로그인 페이지 - 정상호출")
    public void givenNothing_whenTryLogin_thenReturnsLoginView() throws Exception {

        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}
