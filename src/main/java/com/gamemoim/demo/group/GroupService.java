package com.gamemoim.demo.group;

import com.gamemoim.demo.domain.Account;
import com.gamemoim.demo.domain.Group;
import com.gamemoim.demo.domain.GroupManager;
import com.gamemoim.demo.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public void createGroup(Group group, Account account){

        // 그룹 매니저 생성
        GroupManager groupManager = new GroupManager(account);
        group.addManager(groupManager);

        groupRepository.save(group);
    }

    public Group searchGroupByName(String name){
        return groupRepository.findByName(name);
    }

}
