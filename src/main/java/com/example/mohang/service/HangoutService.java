package com.example.mohang.service;

import com.example.mohang.domain.Hangout;
import com.example.mohang.dto.HangoutDto;
import com.example.mohang.entity.HangoutWith;
import com.example.mohang.repository.HangoutRepository;
import com.example.mohang.repository.HangoutWithRepository;
import jakarta.transaction.Transactional;
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
    @Autowired
    HangoutWithRepository hangoutWithRepository;

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
    @Transactional
    public Hangout write(HangoutDto dto) {
        Hangout hangout = new Hangout(dto);
        Hangout written = hangoutRepository.save(hangout);
        String writer = written.getCreatedBy();
        HangoutWith hangwith = new HangoutWith(written, writer, 1);
        hangoutWithRepository.save(hangwith);
        return written;
    }

    public void participate() {

    }
}
