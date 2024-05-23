package com.sparta.devcampauth.login.repository;

import com.sparta.devcampauth.login.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshTokenRepository extends JpaRepository <RefreshToken, Long> {
    void deleteByEmail(String email);
}
