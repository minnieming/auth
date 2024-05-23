package com.sparta.devcampauth.login.service;

import com.sparta.devcampauth.login.dto.LoginRequest;
import com.sparta.devcampauth.login.dto.LoginResponse;
import com.sparta.devcampauth.login.entity.RefreshToken;
import com.sparta.devcampauth.login.jwt.JwtTokenProvider;
import com.sparta.devcampauth.login.repository.RefreshTokenRepository;
import com.sparta.devcampauth.signup.entity.User;
import com.sparta.devcampauth.signup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.sql.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Object login(LoginRequest request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(()-> new RuntimeException("회원정보가 없습니다"));

            String accessToken = jwtTokenProvider.createAccessToken(user.getEmail());
            String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());

            refreshTokenRepository.deleteByEmail(user.getEmail()); // 기존 토큰 삭제
            RefreshToken refreshTokenEntity = new RefreshToken(user.getEmail(), refreshToken, new Date(System.currentTimeMillis() + jwtTokenProvider.getRefreshTokenExpiry()));
            refreshTokenRepository.save(refreshTokenEntity);

            return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
        } catch (BadCredentialsException badCredentialsException) {
            throw new RuntimeException("=====Invalid email/password supplied======");
        }

    }
}
