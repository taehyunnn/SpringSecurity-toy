package com.gamemoim.demo.main;

import com.gamemoim.demo.account.CurrentUser;
import com.gamemoim.demo.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
@Slf4j
public class MainController {
    @GetMapping("/")
    public String mainPage(@CurrentUser Account account, Model model) {
        try{
            if (!Objects.isNull(account)) {
                model.addAttribute("account", account);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
