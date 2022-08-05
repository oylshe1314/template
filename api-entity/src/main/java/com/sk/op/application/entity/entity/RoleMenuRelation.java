package com.sk.op.application.entity.entity;

import com.sk.op.application.entity.entity.base.EntityIdentity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "relationWithApi", attributeNodes = {
                @NamedAttributeNode("menu")
        })
})
public class RoleMenuRelation extends EntityIdentity {

    private Long roleId;

    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuId", insertable = false, updatable = false)
    private Menu menu;
}
