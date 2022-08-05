package com.sk.op.application.admin.service.impl;

import com.sk.op.application.admin.auth.AdminDetails;
import com.sk.op.application.admin.auth.RoleApiAuthority;
import com.sk.op.application.admin.service.AdminService;
import com.sk.op.application.admin.service.AuthService;
import com.sk.op.application.admin.service.RoleMenuService;
import com.sk.op.application.common.exception.StandardRespondException;
import com.sk.op.application.entity.entity.Admin;
import com.sk.op.application.entity.entity.RoleMenuRelation;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
public class AuthServiceImpl implements AuthService {

    private PasswordEncoder passwordEncoder;

    private AdminService adminService;
    private RoleMenuService roleMenuService;

    @Override
    public AdminDetails login(String username, String password) throws AuthenticationException {
        Admin admin = adminService.get(username);
        if (admin == null) {
            throw new UsernameNotFoundException("", new StandardRespondException("用户名或者密码错误"));
        }

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new BadCredentialsException("", new StandardRespondException("用户名或者密码错误"));
        }

        List<RoleMenuRelation> relations = roleMenuService.getApis(admin.getRoleId());

        return new AdminDetails(admin, relations.stream().map(relation -> new RoleApiAuthority(relation.getMenu().getPath())).collect(Collectors.toList()));
    }
}
