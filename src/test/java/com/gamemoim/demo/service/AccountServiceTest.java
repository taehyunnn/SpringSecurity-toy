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
        Account account = new Account("nick", "email", "password");

        //when
        service.save(account);

        Account find = service.findByEmail("email");

        //then
        assertThat(find.getNickname()).isEqualTo(account.getNickname());
    }

    @Test
    public void 중복회원가입() throws Exception{
        //given
        Account account = new Account("nick", "email", "password");

        //given
        Account account2 = new Account("nick", "email", "password");


        //when
        service.save(account);
        service.save(account2);
        //then

    }

}