package com.gamemoim.demo.repository;

import com.gamemoim.demo.domain.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountRepositoryTest {

    @Autowired
    AccountRepository repository;

    @Test
    public void 회원가입() throws Exception{
        //given
        Account account = new Account("닉네임", "email", "password");

        //when
        repository.save(account);

        Account find = repository.findByEmail("email");
        //then

        assertThat(find.getPassword()).isEqualTo(account.getPassword());

    }

}