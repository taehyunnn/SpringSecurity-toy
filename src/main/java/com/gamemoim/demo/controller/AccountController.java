package com.gamemoim.demo.controller;

import com.gamemoim.demo.account.SignUpRequestDto;
import com.gamemoim.demo.account.SignUpValidator;
import com.gamemoim.demo.domain.Account;
import com.gamemoim.demo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService memberService;
    private final SignUpValidator signUpValidator;

    @InitBinder("SignUpRequestDto")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpValidator);
    }

    @GetMapping("/members/sign-up")
    public String signUpForm(Model model){
        model.addAttribute(new SignUpRequestDto());
        return "sign-up";
    }

    @PostMapping("/members/sign-up")
    public String signUpAccount(@Valid SignUpRequestDto requestDto){
        Account account = new Account(requestDto.getNickname(), requestDto.getEmail(), requestDto.getPassword());
        memberService.save(account);

        return "redirect:/";
    }

}
