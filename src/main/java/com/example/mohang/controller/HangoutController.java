package com.example.mohang.controller;

import com.example.mohang.domain.Hangout;
import com.example.mohang.repository.HangoutRepository;
import com.example.mohang.service.HangoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequestMapping("/hangout")
@Controller
public class HangoutController {
    @Autowired private HangoutService service;
    @GetMapping
    public String hangout(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Hangout> paging = service.getList(page);
        model.addAttribute("paging", paging);
        return "/hangout/hangouts";
    }
    @GetMapping("/{id}")
    public String hangout(@PathVariable Long id, Model model) {
        Hangout hangout = service.getHangout(id);
        model.addAttribute("hangout", hangout);
        return "/hangout/hangout";
    }
}
