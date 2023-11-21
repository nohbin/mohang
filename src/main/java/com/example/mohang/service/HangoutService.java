package com.example.mohang.service;

import com.example.mohang.domain.Hangout;
import com.example.mohang.repository.HangoutRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HangoutService {
    @Autowired
    HangoutRepository hangoutRepository;

    public Page<Hangout> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.hangoutRepository.findAll(pageable);
    }
    public Hangout getHangout(Long id) {
        Hangout hangout = hangoutRepository.findById(id).orElse(null);
        return hangout;
    }
}
