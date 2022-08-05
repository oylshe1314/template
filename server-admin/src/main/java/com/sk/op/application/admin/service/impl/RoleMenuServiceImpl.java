package com.sk.op.application.admin.service.impl;

import com.sk.op.application.admin.service.RoleMenuService;
import com.sk.op.application.entity.entity.MenuType;
import com.sk.op.application.entity.entity.RoleMenuRelation;
import com.sk.op.application.entity.entity.State;
import com.sk.op.application.entity.repository.RoleMenuRelationRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
public class RoleMenuServiceImpl implements RoleMenuService {

    private RoleMenuRelationRepository roleApiRelationRepository;

    @Override
    @Transactional
    public void add(Long roleId, Long menuId) {
        RoleMenuRelation relation = new RoleMenuRelation();
        relation.setRoleId(roleId);
        relation.setMenuId(menuId);
        roleApiRelationRepository.save(relation);
    }

    @Override
    @Transactional
    public void add(Long roleId, List<Long> menuIds) {
        List<RoleMenuRelation> relations = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenuRelation relation = new RoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);

            relations.add(relation);
        }
        roleApiRelationRepository.saveAll(relations);
    }

    @Override
    @Transactional
    public void delete(Long roleId, Long menuId) {
        roleApiRelationRepository.deleteByRoleIdAndMenuId(roleId, menuId);
    }

    @Override
    public void delete(Long roleId, List<Long> menuIds) {
        roleApiRelationRepository.deleteByRoleIdAndMenuIdIn(roleId, menuIds);
    }

    private List<RoleMenuRelation> getAll(Long roleId, MenuType... types) {
        return roleApiRelationRepository.findAll((root, query, criteriaBuilder) -> {
            root.fetch("menu");
            CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get("menu").get("type"));
            for (MenuType type : types) {
                in.value(type.value());
            }
            return query.where(criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("roleId"), roleId),
                            criteriaBuilder.equal(root.get("menu").get("state"), State.enable.value()),
                            in
                    ))
                    .getRestriction();
        });
    }

    @Override
    public List<RoleMenuRelation> getMenus(Long roleId) {
        return this.getAll(roleId, MenuType.directory, MenuType.menu);
    }

    @Override
    public List<RoleMenuRelation> getApis(Long roleId) {
        return this.getAll(roleId, MenuType.api);
    }
}
