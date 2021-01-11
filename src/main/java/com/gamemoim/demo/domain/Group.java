package com.gamemoim.demo.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "groups") // table 명으로 group No
public class Group extends  BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    private String name;

    private String description;

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void changeName(String name){
        this.name = name;
    }

    public void changeDescription(String description){
        this.description = description;
    }
}
