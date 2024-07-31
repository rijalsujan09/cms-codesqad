package com.codesqad.cms.account.controller;

import com.codesqad.cms.account.entity.Authority;
import com.codesqad.cms.account.entity.Role;
import com.codesqad.cms.account.service.AuthorityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class AuthorityController {
    @Autowired
    private AuthorityService authorityService;
    @PostMapping("/create")
    public ResponseEntity<?> createRole(@Valid @RequestBody Authority authorityDto) {
        Authority authority = this.authorityService.createRole(authorityDto);
        return new ResponseEntity<>(authority,HttpStatus.CREATED);
    }
}
