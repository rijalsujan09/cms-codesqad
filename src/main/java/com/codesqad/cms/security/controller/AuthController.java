package com.codesqad.cms.security.controller;

import com.codesqad.cms.account.dto.UserDtoSave;
import com.codesqad.cms.security.dto.AuthRequest;
import com.codesqad.cms.security.dto.AuthResponse;
import com.codesqad.cms.security.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse>register(@RequestBody UserDtoSave registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>register(@RequestBody AuthRequest authRequest) {
        logger.info(String.format("Auth request: %s", authRequest.toString()));
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }
    @PostMapping("/verify")
    public ResponseEntity<?>verify(HttpServletRequest request) {
        logger.info(String.format("Auth request: %s", request.getHeader("Authorization")));
        return ResponseEntity.ok(authService.verify(request.getHeader("Authorization")));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().flush();
    }
}
