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

    public Page<Hangout> getList(Pageable pageable) {
        return this.hangoutRepository.findAll(pageable);
    }
    public Hangout getHangout(Long id) {
        Hangout hangout = hangoutRepository.findById(id).orElse(null);
        return hangout;
    }
    public Page<Hangout> search(String cate, String keyword, Pageable pageable) {
        if(cate.equals("title")) {
            return hangoutRepository.findByTitleContaining(keyword, pageable);
        } else if(cate.equals("content")) {
            return hangoutRepository.findByContentContaining(keyword, pageable);
        } else if(cate.equals("createdBy")) {
            return hangoutRepository.findByCreatedByContaining(keyword, pageable);
        } else {
            return null;
        }
    }
}
