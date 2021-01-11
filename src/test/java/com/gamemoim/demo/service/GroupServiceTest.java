package com.gamemoim.demo.service;

import com.gamemoim.demo.account.AccountRepository;
import com.gamemoim.demo.domain.Account;
import com.gamemoim.demo.domain.Group;
import com.gamemoim.demo.domain.GroupManager;
import com.gamemoim.demo.group.GroupService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class GroupServiceTest {

    @Autowired
    GroupService service;

    @Autowired
    AccountRepository accountRepository;


    @Test
    public void 그룹생성() throws Exception {
        //given
        Account account = new Account("nick", "email", "password");


        Group group = Group.createGroup("name","desc");


        service.createGroup(group, account);
        //when

        accountRepository.save(account);
        Group findGroup = service.searchGroupByName("name");

        //then
        assertThat(findGroup.getDescription()).isEqualTo(group.getDescription());

    }

}