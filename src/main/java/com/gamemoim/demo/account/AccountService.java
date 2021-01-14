package com.gamemoim.demo.account;

import com.gamemoim.demo.config.AppProperties;
import com.gamemoim.demo.domain.Account;
import com.gamemoim.demo.email.EmailMessage;
import com.gamemoim.demo.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService implements UserDetailsService {

    private final AccountRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final AppProperties appProperties;

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

    public Account findByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    public void login(Account account) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(account,
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public Account createNewAccount(SignUpRequestDto requestDto) {
        Account account = new Account(requestDto.getNickname(), requestDto.getEmail(), requestDto.getPassword());
        account.changePassword(passwordEncoder.encode(account.getPassword()));
        account.generateEmailCheckToken();
        memberRepository.save(account);
        sendSignUpConfirmEmail(account);
        return account;
    }

    private void sendSignUpConfirmEmail(Account newAccount) {
        Context context = new Context();
        context.setVariable("link", "/check-email-token?token=" + newAccount.getEmailCheckToken() +
                "&email=" + newAccount.getEmail());
        context.setVariable("nickname", newAccount.getNickname());
        context.setVariable("linkName", "이메일 인증하기");
        context.setVariable("message", "회원가입을 완료하시려면 링크를 클릭하세요.");
        context.setVariable("host", appProperties.getHost());
        String message = templateEngine.process("email/link", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(newAccount.getEmail())
                .subject("게임모임, 이메일 인증")
                .message(message)
                .build();

        emailService.sendEmail(emailMessage);
    }
}
