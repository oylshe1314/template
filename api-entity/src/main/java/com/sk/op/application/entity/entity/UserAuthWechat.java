package com.sk.op.application.entity.entity;

import com.sk.op.application.entity.entity.base.EntityIdentity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class UserAuthWechat extends EntityIdentity {

    private Long userId;

    private String openId;

    private String sessionKey;
}
