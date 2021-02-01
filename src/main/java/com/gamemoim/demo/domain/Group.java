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

    // 나중엔 비밀번호방도 추가해보자
    private String password;

    private String description;

    @Column(unique = true)
    private String path;

    private boolean access;

    private boolean participation;

    private int memberCount;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Map<Long, GroupManager> managers = new HashMap<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Map<Long, GroupMember> members = new HashMap<>();

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
        return members.containsKey(account.getId());
    }

    public boolean isManager(Account account) {
        return managers.containsKey(account.getId());
    }

    public boolean isManagedBy(Account account) {
        return this.getManagers().containsValue(account);
    }

    // 비번방 해제
    public void unlock() {
        if(!this.access){
            this.access = true;
            this.setLastModifiedDate(LocalDateTime.now());
        }else{
            throw new RuntimeException("unlock group error");
        }
    }

    // 비번방으로 설정
    public void lock(){
        if(this.access){
            this.access = false;
            this.setLastModifiedDate(LocalDateTime.now());
        }else{
            throw new RuntimeException("lock group error");
        }
    }

    // 멤버 모집 시작(재개)
    public void startRecruit() {
        if(!isParticipation()){
            this.participation = true;
        }else{
            throw new RuntimeException("인원 모집 불가");
        }
    }

    // 멤버 모집 중단
    public void stopRecruit() {
        if (isParticipation()) {
            this.participation = false;
        } else {
            throw new RuntimeException("인원 모집 중단 불가");
        }
    }

    // 양방향 연관관계
    public void addManager(GroupManager groupManager) {
        this.getManagers().put(groupManager.getId(), groupManager);
        groupManager.setGroup(this);
        memberCount++;
    }

    public void removeMember(Account account){
        this.getMembers().remove(account);
        this.memberCount--;
    }
}
