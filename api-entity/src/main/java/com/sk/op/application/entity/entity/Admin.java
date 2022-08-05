package com.sk.op.application.entity.entity;

import com.sk.op.application.entity.entity.base.EntityOperation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "adminWithRole", attributeNodes = {
                @NamedAttributeNode("role")
        })
})
public class Admin extends EntityOperation {

    private Long roleId;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private String email;

    private String mobile;

    private Integer state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", insertable = false, updatable = false)
    private Role role;
}
