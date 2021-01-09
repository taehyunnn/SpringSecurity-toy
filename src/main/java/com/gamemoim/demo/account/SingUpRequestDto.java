package com.gamemoim.demo.account;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SingUpRequestDto {

    @NotBlank
    @Length(min = 3, max = 20)
    private String nickname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max = 20)
    private String password;
}
