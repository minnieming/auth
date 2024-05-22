package com.sparta.devcampauth.signup.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.aspectj.bridge.Message;

@Getter
public class SignupRequest {

    @NotBlank (message = "이름은 빈 값일 수 없습니다.")
    private String username;

    @NotBlank (message = "비밀번호는 빈 값일 수 없습니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W)[a-zA-Z0-9\\W]{8,15}$",
            message = "대문자, 소문자, 특수문자, 숫자 포함 8~15자리.")
    private String password;

    @Email
    @NotBlank (message = "이메일은 빈 값일 수 없습니다.")
    private String email;

}
