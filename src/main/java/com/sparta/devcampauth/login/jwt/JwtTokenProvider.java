package com.sparta.devcampauth.login.jwt;

import com.sparta.devcampauth.login.security.UserDetailsServiceImpl;
import com.sparta.devcampauth.signup.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.Decoder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Getter
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}") // annotation "value"
    private String secretKey;
    private SecretKey key;
    private MacAlgorithm algorithm;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostConstruct
    public void init () {
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
        algorithm = Jwts.SIG.HS256;
    }

    @Value("${jwt.access-token-expiry}")
    private long accessTokenExpiry; // = 60 * 60 * 1000; // 60분

    @Value("${jwt.refresh-token-expiry}")
    private long refreshTokenExpiry; // = 60 * 60 * 100000; // 10시간

    // accessToken 생성
    public String createAccessToken (String email) {

        Date now = new Date(); // import 해서 사용
        Date expireDate = new Date(now.getTime() + accessTokenExpiry);

        return Jwts.builder()
                .subject(email) // 사용자 식별자값 (ID?)
                .issuedAt(now) // 발급일
                .expiration(expireDate) // accessToken 만료 시간
                .signWith(key,algorithm) // 암호화 알고리즘
                .compact();
    }

    // refreshToken 생성
    public String createRefreshToken (String email) {

        Date now = new Date();
        Date expireDate = new Date (now.getTime() + refreshTokenExpiry);

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(key, algorithm)
                .compact();
    }

    // 토큰 검증
    public boolean validateToken (String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseEncryptedClaims(token);
            return true;
        } catch (ExpiredJwtException jwtException) {
            log.error("만료된 JWT Token입니다");
        } catch (Exception exception) {
            log.error ("유효하지 않은 JWT Token입니다");
        }
        return false;
    }

    // http 헤더로부터 bearer 토큰을 가져옴
    public String getJwtBearerFromHeader (HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication (String token) {

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String email = claims.getSubject();

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, "", Collections.emptyList());


    }

}
