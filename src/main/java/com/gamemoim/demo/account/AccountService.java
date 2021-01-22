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

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final AppProperties appProperties;
    private final EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account findUser = accountRepository.findByEmail(email);

        /**
         * todo : db에서 권한 정보 가져와서 적용하는 걸로 리팩토링
         */
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new UserAccount(findUser);
    }

    public Account findByEmail(String email){
        return accountRepository.findByEmail(email);
    }

    public void login(Account account) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Transactional
    public Account createProcessNewAccount(@Valid SignUpRequestDto requestDto) {
        Account account = saveNewAccount(requestDto);
        sendSignUpConfirmEmail(account);
        return account;
    }

    private Account saveNewAccount(@Valid SignUpRequestDto requestDto) {
        Account account = new Account(requestDto.getNickname(), requestDto.getEmail(), requestDto.getPassword());
        account.changePassword(passwordEncoder.encode(account.getPassword()));
        account.generateEmailCheckToken();
        accountRepository.save(account);
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

    public void sendLoginLink(Account account) {
        Context context = new Context();
        context.setVariable("link", "/login-by-email?token=" + account.getEmailCheckToken() +
                "&email=" + account.getEmail());
        context.setVariable("nickname", account.getNickname());
        context.setVariable("linkName", "게임모임 로그인하기");
        context.setVariable("message", "로그인 하려면 아래 링크를 클릭하세요.");
        context.setVariable("host", appProperties.getHost());
        String message = templateEngine.process("email/link", context);


        EmailMessage emailMessage = EmailMessage.builder()
                .to(account.getEmail())
                .subject("게임모임, 로그인 링크")
                .message(message)
                .build();

        emailService.sendEmail(emailMessage);
    }

    @Transactional
    public void completeSignUp(Account account) {
        account.completeSignUp();
        login(account);
    }
}
