package com.gamemoim.demo.controller;

import com.gamemoim.demo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService memberService;


    @GetMapping("/members/signup")
    public String joinMember(){
        return "singup";
    }


}
