package com.sk.op.application.admin.dto;

import com.sk.op.application.entity.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色菜单")
public class RoleMenuDto {

    @Schema(description = "父级ID")
    private Long parentId;

    @Schema(description = "类型: 1.目录, 2.菜单")
    private Integer type;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "路径")
    private String path;

    @Schema(description = "排序值")
    private Integer sortBy;

    @Schema(description = "子菜单")
    private List<RoleMenuDto> subMenu;

    public RoleMenuDto(Menu menu) {
        this(
                menu.getParentId(),
                menu.getType(),
                menu.getName(),
                menu.getPath(),
                menu.getSortBy(),
                new ArrayList<>()
        );
    }
}
