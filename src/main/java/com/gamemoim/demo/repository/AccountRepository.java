package com.gamemoim.demo.repository;

import com.gamemoim.demo.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);

    Account findByNickName(String nickName);
    Account findByEmail(String email);
}
