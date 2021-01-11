package com.gamemoim.demo.service;

import com.gamemoim.demo.domain.Group;
import com.gamemoim.demo.group.GroupCreateRequestDto;
import com.gamemoim.demo.repository.GroupRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GroupServiceTest {

    @Autowired
    GroupService service;

    @Test
    public void 그룹생성() throws Exception{
        //given
        GroupCreateRequestDto dto = new GroupCreateRequestDto();

        dto.setName("그룹1");
        dto.setDescription("설명1");
        service.createGroup(dto);

        //when
        Group findGroup = service.searchGroupByName("그룹1");

        //then
        assertThat(findGroup.getDescription()).isEqualTo(dto.getDescription());

    }

}