package com.sk.op.application.house.service.impl;

import com.sk.op.application.house.service.AuthService;
import com.sk.op.application.user.auth.UserDetails;

public class AuthServiceImpl implements AuthService {


    @Override
    public UserDetails byPassword(String account, String password) {
        return null;
    }

    @Override
    public UserDetails bySms(String phone, String smsCode) {
        return null;
    }

    @Override
    public UserDetails byWechat(String code) {
        return null;
    }
}
