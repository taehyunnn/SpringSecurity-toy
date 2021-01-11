package com.gamemoim.demo.account;

import com.gamemoim.demo.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickName);

    Account findByNickname(String nickName);
    Account findByEmail(String email);
}
