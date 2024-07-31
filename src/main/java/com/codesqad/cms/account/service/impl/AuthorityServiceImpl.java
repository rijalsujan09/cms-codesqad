package com.codesqad.cms.account.service.impl;

import com.codesqad.cms.account.entity.Authority;
import com.codesqad.cms.account.entity.Role;
import com.codesqad.cms.account.repository.AuthorityRepository;
import com.codesqad.cms.account.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority createRole(Authority authorityDto) {
        return this.authorityRepository.save(authorityDto);
    }
}
