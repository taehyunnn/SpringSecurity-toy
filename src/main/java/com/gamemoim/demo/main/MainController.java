package com.gamemoim.demo.main;

import com.gamemoim.demo.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(Account account, Model model) {
        if (!Objects.isNull(account)) {
            model.addAttribute(account);
        }
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
