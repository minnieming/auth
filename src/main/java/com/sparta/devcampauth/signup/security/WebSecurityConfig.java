package com.sparta.devcampauth.signup.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception { // Security 에서 인증/인가 제어를 위해 관리하는 Filter 설정
        http
                .csrf(AbstractHttpConfigurer::disable) // API 서버의 경우 일반적으로 CSRF 공격이 발생하지 않으므로 CSRF 보호를 비활성화 | 람다(.csrf((csrf) -> csrf.disable())) 아닌 spring 메서드로 작성
                .authorizeHttpRequests((authHttpRequest) -> // API 제어 설정 : 요청된 URL 기반으로 인증/인가 제어 및 접근 권한 설정
                        authHttpRequest
                                .requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated()); // 그 외 모든 요청 인증처리 진행

                return http.build(); // 설정을 적용하고 SecurityFilterChain을 반환
    }

    @Bean
    public PasswordEncoder passwordEncoder () { // 비밀번호를 암호화하기 위해 BCryptPasswordEncoder. 사용자 비밀번호를 안전하게 저장하고 검증하는 데 사용
        return new BCryptPasswordEncoder();
    }
}
