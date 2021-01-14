package com.gamemoim.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Table(name = "groups") // table 명으로 group No
public class Group extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Map<Long, GroupManager> groupManagers = new HashMap();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Map<Long, GroupMember> groupMembers = new HashMap();

    public static Group createGroup(String name, String description){
        Group group = new Group();
        group.changeName(name);
        group.changeDescription(description);

        group.setCreatedDate(LocalDateTime.now());
        group.setLastModifiedDate(LocalDateTime.now());
        return group;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public boolean isMember(Account account) {
        return groupMembers.containsKey(account.getId());
    }

    public boolean isManager(Account account) {
        return groupManagers.containsKey(account.getId());
    }

    // 양방향 연관관계
    public void addManager(GroupManager groupManager) {
        this.getGroupManagers().put(groupManager.getId(), groupManager);
        groupManager.setGroup(this);
    }
}
