package com.sk.op.application.admin.service;

import com.sk.op.application.entity.entity.Menu;
import com.sk.op.application.entity.entity.RoleMenuRelation;

import java.util.List;

public interface RoleMenuService {

    void add(Long roleId, Long menuId);

    void add(Long roleId, List<Long> menuIds);

    void delete(Long roleId, Long menuId);

    void delete(Long roleId, List<Long> menuIds);

    List<RoleMenuRelation> getMenus(Long roleId);

    List<RoleMenuRelation> getApis(Long roleId);
}
