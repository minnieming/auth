package com.sparta.devcampauth.login.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Request 이전에 작동
// 헤더(Authorization)에 있는 토큰을 꺼내 이상이 없는 경우 SecurityContext에 저장
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal (HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {

        try {
            // 클라이언트가 보낸 JWT 토큰을 가져옴
            String jwtToken = jwtTokenProvider.getJwtBearerFromHeader(request);

            // 토큰이 비어있거나 유효하지 않은 경우
            if (StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)) {
                // 토큰을 사용하여 사용자 인증
                Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);

                // SecurityContext에 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            // 예외 처리
            logger.error("Could not set user authentication in security context", ex);
        }

        // 다음 필터로 이동
        filterChain.doFilter(request, response);

    }

}
