package com.sparta.devcampauth.signup.controller;

import com.sparta.devcampauth.login.service.AuthService;
import com.sparta.devcampauth.login.dto.LoginRequest;
import com.sparta.devcampauth.signup.dto.SignupRequest;
import com.sparta.devcampauth.signup.dto.SignupResponse;
import com.sparta.devcampauth.signup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class SignupController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping ("/signup")
    public ResponseEntity <SignupResponse> signup (@RequestBody SignupRequest request) {
        return ResponseEntity.ok(userService.signup(request));
    }

    @PostMapping ("/login")
    public ResponseEntity <?> login (@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
