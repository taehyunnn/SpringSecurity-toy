package com.gamemoim.demo.service;

import com.gamemoim.demo.domain.Group;
import com.gamemoim.demo.group.GroupCreateRequestDto;
import com.gamemoim.demo.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository repository;

    public void createGroup(@Valid GroupCreateRequestDto requestDto){
        Group group = new Group(requestDto.getName(), requestDto.getDescription());

        repository.save(group);
    }

    public Group searchGroupByName(String name){
        return repository.findByName(name);
    }

}
