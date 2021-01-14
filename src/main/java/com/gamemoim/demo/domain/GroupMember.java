package com.gamemoim.demo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMember {

    @Id
    @GeneratedValue
    @Column(name = "group_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public GroupMember(Account account, Group group) {
        this.account = account;
        this.group = group;
    }

    public void addGroupMember(Account account, Group group){
        this.account = account;
        this.group = group;
        account.getGroupMembers().add(this);
        group.getGroupMembers().put(this.getId(), this);
    }
}
