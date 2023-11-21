package com.example.mohang.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

        mvc.perform(get("/hangapi/hangouts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json;charset=UTF-8")));
    }

    @DisplayName("[API] 게시글 조회")
    @Test
    void given_whenRequestingHangout_thenReturnsHangoutJson() throws Exception {

        mvc.perform(get("/hangapi/hangouts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json;charset=UTF-8")));
    }



}
