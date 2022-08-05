package com.sk.op.application.house.service;

import com.sk.op.application.user.auth.UserDetails;

public interface AuthService {

    UserDetails byPassword(String account, String password);

    UserDetails bySms(String phone, String smsCode);

    UserDetails byWechat(String code);
}
