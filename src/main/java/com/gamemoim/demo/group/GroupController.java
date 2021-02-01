package com.gamemoim.demo.group;

import com.gamemoim.demo.account.CurrentUser;
import com.gamemoim.demo.domain.Account;
import com.gamemoim.demo.domain.Group;
import com.gamemoim.demo.domain.GroupManager;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final GroupValidator groupValidator;
    private final GroupRepository groupRepository;

    @InitBinder("groupCreateRequestDto")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(groupValidator);
    }

    @GetMapping("/group-list")
    public String showGroupList(Model model) {
        List groupList = groupRepository.findAll();
        model.addAttribute("list",groupList);
        return "group/list";
    }

    @GetMapping("/create-group")
    public String createGroupForm(Model model){
        model.addAttribute(new GroupCreateRequestDto());
        return "group/form";
    }

    @PostMapping("/create-group")
    public String createGroup(@Valid GroupCreateRequestDto requestDto, @CurrentUser Account account){

        Group group = Group.createGroup(requestDto.getName(), requestDto.getDescription());

        groupService.createGroup(group,account);

        return "redirect:/";
    }
}
