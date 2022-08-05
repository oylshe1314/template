package com.sk.op.application.admin.controller;

import com.sk.op.application.admin.auth.AdminDetails;
import com.sk.op.application.admin.dto.RoleMenuDto;
import com.sk.op.application.admin.service.RoleMenuService;
import com.sk.op.application.common.dto.ResponseDto;
import com.sk.op.application.entity.entity.Menu;
import com.sk.op.application.entity.entity.RoleMenuRelation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/common")
@Tag(name = "common", description = "公共接口")
public class CommonController {

    private final RoleMenuService roleMenuService;

    public CommonController(RoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    private void sortMenus(List<RoleMenuDto> roleMenus) {
        if (roleMenus.isEmpty()) {
            return;
        }
        roleMenus.sort(Comparator.comparingInt(RoleMenuDto::getSortBy));
        roleMenus.forEach(menu -> {
            sortMenus(menu.getSubMenu());
        });
    }

    @Operation(summary = "角色菜单")
    @RequestMapping(value = "/role/menus", method = RequestMethod.GET)
    public ResponseDto<List<RoleMenuDto>> roleMenus(Authentication authentication) {
        AdminDetails adminDetails = (AdminDetails) authentication.getPrincipal();
        List<RoleMenuRelation> relations = roleMenuService.getMenus(adminDetails.getRoleId());
        Map<Long, RoleMenuDto> roleMenuMap = new HashMap<>();
        for (RoleMenuRelation relation : relations) {
            roleMenuMap.put(relation.getMenuId(), new RoleMenuDto(relation.getMenu()));
        }

        List<RoleMenuDto> roleMenus = new ArrayList<>();
        roleMenuMap.forEach((menuId, menu) -> {
            if (menu.getParentId() == 0) {
                roleMenus.add(menu);
            } else {
                RoleMenuDto parent = roleMenuMap.get(menu.getParentId());
                if (parent != null) {
                    parent.getSubMenu().add(menu);
                }
            }
        });

        sortMenus(roleMenus);

        return ResponseDto.succeed(roleMenus);
    }
}
