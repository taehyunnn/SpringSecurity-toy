package com.gamemoim.demo.account;

import com.gamemoim.demo.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService implements UserDetailsService {

    private final AccountRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account findUser = memberRepository.findByEmail(email);

        /**
         * todo : db에서 권한 정보 가져와서 적용하는 걸로 리팩토링
         */
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(findUser.getEmail(), findUser.getPassword(), authorities);
    }

    public Account save(Account account) {
        account.changePassword(passwordEncoder.encode(account.getPassword()));
        return memberRepository.save(account);
    }

    public Account findByEmail(String email){
        return memberRepository.findByEmail(email);
    }

}
