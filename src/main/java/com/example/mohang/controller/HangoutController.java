package com.example.mohang.controller;

import com.example.mohang.domain.Hangout;

import com.example.mohang.dto.HangoutDto;
import com.example.mohang.repository.HangoutRepository;

import com.example.mohang.service.HangoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/hangout")
@Controller
public class HangoutController {
    @Autowired private HangoutService service;
    @GetMapping
    public String hangout(Model model,
                          @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                          @RequestParam(value="page", defaultValue="0") int page) {
        Page<Hangout> paging = service.getList(pageable);
        model.addAttribute("paging", paging);
        return "/hangout/hangouts";
    }
    @GetMapping("/{id}")
    public String hangout(@PathVariable Long id, Model model) {
        Hangout hangout = service.getHangout(id);
        model.addAttribute("hangout", hangout);
        return "/hangout/hangout";
    }
    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam("cate") String cate,
                         @RequestParam("keyword") String keyword,
                         @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam(value="page", defaultValue="0") int page) {
        Page<Hangout> paging = service.search(cate, keyword, pageable);
        model.addAttribute("paging", paging);
        model.addAttribute("keyword", keyword);
        model.addAttribute("cate", cate);
        return "/hangout/hangouts";
    }

    @GetMapping("/write/form")
    public String writeForm() {
        return "/hangout/write";
    }
    @PostMapping
    public String write(Model model, HangoutDto dto) {
        Hangout written = service.write(dto);
        model.addAttribute("hangout", written);
        return "/hangout/hangout";
    }

}
