package com.sk.op.application.admin.auth;

import com.sk.op.application.entity.entity.Admin;
import com.sk.op.application.entity.entity.State;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@Setter
public class AdminDetails implements UserDetails {

    private Long id;

    private Long roleId;

    private String roleName;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private String email;

    private String mobile;

    private Integer state;

    private List<RoleApiAuthority> authorities;

    public AdminDetails(Admin admin, List<RoleApiAuthority> authorities) {
        this.id = admin.getId();
        this.roleId = admin.getRole().getId();
        this.roleName = admin.getRole().getName();
        this.username = admin.getUsername();
        this.password = admin.getPassword();
        this.nickname = admin.getNickname();
        this.avatar = admin.getAvatar();
        this.email = admin.getEmail();
        this.mobile = admin.getMobile();
        this.state = admin.getState();
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.state == State.enable.value();
    }
}
