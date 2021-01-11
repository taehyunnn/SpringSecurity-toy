package com.gamemoim.demo.service;

import com.gamemoim.demo.account.SignUpRequestDto;
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
class AccountServiceTest {

    @Autowired AccountService service;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception{
        //given
        SignUpRequestDto req = new SignUpRequestDto();

        req.setNickname("닉네임");
        req.setEmail("email");
        req.setPassword("password");

        //when
        service.save(req);

        Account find = service.findByEmail("email");

        //then
        assertThat(find.getNickName()).isEqualTo(req.getNickname());
    }

    @Test
    public void 중복회원가입() throws Exception{
        //given
        SignUpRequestDto req1 = new SignUpRequestDto();
        SignUpRequestDto req2 = new SignUpRequestDto();

        req1.setNickname("닉네임");
        req1.setEmail("email");
        req1.setPassword("password");

        req2.setNickname("닉네임");
        req2.setEmail("email");
        req2.setPassword("password2");

        //when
        service.save(req1);
        service.save(req2);
        //then

    }

}