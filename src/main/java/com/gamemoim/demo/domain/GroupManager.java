package com.gamemoim.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GroupManager {

    @Id
    @GeneratedValue
    @Column(name = "group_manager_id")
    private Long id;

    @JoinColumn(name = "account_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @JoinColumn(name = "group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    public GroupManager(Account account) {
        this.account = account;
    }

    public void addGroupManager(Account account, Group group) {
        this.account = account;
        this.group = group;
        account.getGroupManagers().add(this);
        group.getGroupManagers().put(this.getId(), this);
    }
}
