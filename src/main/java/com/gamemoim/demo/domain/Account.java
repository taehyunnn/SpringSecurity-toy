package com.gamemoim.demo.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Account extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @OneToMany(mappedBy = "account")
    private List<GroupManager> groupManagers = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<GroupMember> groupMembers = new ArrayList<>();

    public Account(String nickName, String email, String password) {
        this.nickname = nickName;
        this.email = email;
        this.password = password;
    }

    public void changeNickName(String nickName){
        this.nickname = nickName;
    }

    public void changePassword(String password){
        this.password = password;
    }

}
