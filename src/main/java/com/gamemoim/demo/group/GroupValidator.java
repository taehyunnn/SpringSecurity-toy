package com.gamemoim.demo.group;

import com.gamemoim.demo.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class GroupValidator implements Validator {

    private final GroupRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(GroupCreateRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Group group = (Group) target;

        if(repository.existsByName(group.getName())){
            errors.rejectValue("name","invalid.name",new Object[]{((Group) target).getName()}, "이미 존재하는 이름입니다");
        }
    }
}
