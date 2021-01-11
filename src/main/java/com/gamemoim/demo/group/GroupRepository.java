package com.gamemoim.demo.group;

import com.gamemoim.demo.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findByName(String name);
    boolean existsByName(String name);
}
