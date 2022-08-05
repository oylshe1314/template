package com.sk.op.application.entity.entity;

import com.sk.op.application.entity.entity.base.EntityIdentity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class User extends EntityIdentity {

    private String username;

    private String password;

    private String phone;

    private String email;
}
