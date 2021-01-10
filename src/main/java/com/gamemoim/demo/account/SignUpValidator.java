package com.gamemoim.demo.account;

import com.gamemoim.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
public class SignUpValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpRequestDto singUpRequestDto = (SignUpRequestDto) target;

        if(accountRepository.existsByEmail((singUpRequestDto).getEmail())){
            errors.rejectValue("email","invalid.email", new Object[]{singUpRequestDto.getEmail()}, "이미 존재하는 이메일입니다");
        }
        if(accountRepository.existsByNickName(singUpRequestDto.getNickname())){
            errors.rejectValue("nickName","invalid.nickName", new Object[]{singUpRequestDto.getNickname()}, "이미 존재하는 닉네임입니다");
        }
    }
}
