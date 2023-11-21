package com.example.mohang.controller;

import com.example.mohang.config.SecurityConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@Import(SecurityConfig.class)
@DisplayName("Data Rest - API Test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DataRestTest {

    private final MockMvc mvc;

    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }


    @DisplayName("[API] 게시글 전체 조회")
    @Test
    void given_whenRequestingHangouts_thenReturnsHangoutJson() throws Exception {

        mvc.perform(get("/hangapi/hangoutses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json;charset=UTF-8")));
    }

    @DisplayName("[API] 게시글 조회")
    @Test
    void given_whenRequestingHangout_thenReturnsHangoutJson() throws Exception {

        mvc.perform(get("/hangapi/hangoutses/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json;charset=UTF-8")));
    }

    @DisplayName("[API] 회원 관련 API 는 일체 제공하지 않는다.")
    @Test
    void givenNothing_whenRequestingUserAccounts_thenThrowsException() throws Exception {

        mvc.perform(get("/hangapi/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(post("/hangapi/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(put("/hangapi/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(patch("/hangapi/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(delete("/hangapi/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(head("/hangapi/userAccounts")).andExpect(status().isNotFound());

    }

}
