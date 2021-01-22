package com.gamemoim.demo.service;

import com.gamemoim.demo.account.AccountService;
import com.gamemoim.demo.account.SignUpRequestDto;
import com.gamemoim.demo.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AccountServiceTest {

    @Autowired
    AccountService service;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception{
        //given
        SignUpRequestDto account = new SignUpRequestDto("nick", "email", "password");

        //when
        service.createProcessNewAccount(account);

        Account find = service.findByEmail("email");

        //then
        assertThat(find.getNickname()).isEqualTo(account.getNickname());
    }

    @Test
    public void 중복회원가입() throws Exception{
        //given
        SignUpRequestDto account = new SignUpRequestDto("nick", "email", "password");
        SignUpRequestDto account2 = new SignUpRequestDto("nick", "email", "password");



        //when
        service.createProcessNewAccount(account);
        service.createProcessNewAccount(account2);
        //then

    }

}