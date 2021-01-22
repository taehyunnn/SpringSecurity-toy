package com.gamemoim.demo.account;

import com.gamemoim.demo.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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

    @InitBinder("signUpRequestDto")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute(new SignUpRequestDto());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpAccount(@Valid SignUpRequestDto requestDto, Errors errors){
        if(errors.hasErrors()){
            return "account/sign-up";
        }

        Account newAccount = memberService.createProcessNewAccount(requestDto);
        memberService.login(newAccount);
        return "redirect:/";
    }


}
