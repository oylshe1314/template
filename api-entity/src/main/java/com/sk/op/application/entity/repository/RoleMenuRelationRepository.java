package com.sk.op.application.entity.repository;

import com.sk.op.application.entity.entity.RoleMenuRelation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RoleMenuRelationRepository extends JpaRepository<RoleMenuRelation, Long>, JpaSpecificationExecutor<RoleMenuRelation> {

    void deleteByRoleIdAndMenuId(Long roleId, Long apiId);

    void deleteByRoleIdAndMenuIdIn(Long roleId, Collection<Long> apiId);
}
