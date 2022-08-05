package com.sk.op.application.admin.service;


import com.sk.op.application.entity.entity.Admin;

public interface AdminService extends BaseService<Admin, Long> {

    Admin get(String username);
}
