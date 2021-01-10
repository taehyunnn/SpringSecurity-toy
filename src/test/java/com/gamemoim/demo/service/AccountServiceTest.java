package com.gamemoim.demo.service;

import com.gamemoim.demo.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class AccountServiceTest {

    @Autowired AccountService service;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception{
        //given
        Account account = new Account("닉네임", "email", "password");

        //when
        service.save(account);

        em.flush();
        em.clear();

        Account find = service.findByEmail("email");

        //then
        assertThat(find.getPassword()).isEqualTo(account.getPassword());
    }

}