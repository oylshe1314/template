package com.sk.op.application.admin.service.impl;

import com.sk.op.application.admin.service.AdminService;
import com.sk.op.application.entity.entity.Admin;
import com.sk.op.application.entity.repository.AdminRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Setter(onMethod_ = @Autowired)
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    @Override
    public Admin get(String username) {
        return adminRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Admin get(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Admin> query(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    @Override
    public Page<Admin> query(Pageable pageable, Admin params) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        if (params.getUsername() != null) {
            matcher = matcher.withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        if (params.getNickname() != null) {
            matcher = matcher.withMatcher("nickname", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        if (params.getEmail() != null) {
            matcher = matcher.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        return adminRepository.findAll(Example.of(params, matcher), pageable);
    }
}
