package com.codesqad.cms.security.service;

import com.codesqad.cms.account.dto.UserDtoSave;
import com.codesqad.cms.account.entity.Role;
import com.codesqad.cms.account.entity.UserAccount;
import com.codesqad.cms.account.repository.AuthorityRepository;
import com.codesqad.cms.account.repository.UserRepository;
import com.codesqad.cms.security.dto.AuthRequest;
import com.codesqad.cms.security.dto.AuthResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthorityRepository roleRepository;

    public AuthResponse register(UserDtoSave request) {
        UserAccount usr = UserAccount.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        UserAccount user = userRepository.save(usr);
        UserDetails user1 = User.builder().username(user.getEmail()).password(user.getPassword()).authorities(List.of(new SimpleGrantedAuthority(user.getRole().toString()))).build();
        return AuthResponse.builder().xAuthToken(jwtService.generateToken(user1)).build();
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        var user = authRequest.getUsername().contains("@")? userRepository.findByEmail(authRequest.getUsername()):userRepository.findByUsername(authRequest.getUsername());
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().toString()));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword(),authorities));
        UserDetails user1 = User.builder().username(user.getEmail()).password(user.getPassword()).authorities(authorities).build();
        logger.info(String.format("Auth request: %s", jwtService.generateToken(user1)));
        return AuthResponse.builder().xAuthToken(jwtService.generateToken(user1)).build();
    }

    public Boolean verify(String token) {
        String username = jwtService.extractUsername(token);
        UserAccount user = userRepository.findByUsername(username);
        UserDetails user1 = User.builder().username(user.getEmail()).password(user.getPassword()).build();
        return jwtService.isTokenValid(token,user1);
    }
}
