package com.sparta.devcampauth.signup.service;

import com.sparta.devcampauth.signup.dto.SignupRequest;
import com.sparta.devcampauth.signup.dto.SignupResponse;
import com.sparta.devcampauth.signup.entity.User;
import com.sparta.devcampauth.signup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponse signup (SignupRequest request) {

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isPresent()) {
            throw new RuntimeException(request.getEmail() + "는 이미 존재하는 이메일입니다.");
        }

        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(newUser);

        return new SignupResponse(newUser.getUsername() + "님의 회원가입이 완료되었습니다.");
    }

}
