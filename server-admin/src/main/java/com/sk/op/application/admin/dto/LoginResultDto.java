package com.sk.op.application.admin.dto;

import com.sk.op.application.admin.auth.AdminDetails;
import com.sk.op.application.entity.entity.Admin;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "登录结果信息")
public class LoginResultDto {

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String mobile;

    public LoginResultDto(Admin admin) {
        this(
                admin.getRole().getName(),
                admin.getUsername(),
                admin.getNickname(),
                admin.getAvatar(),
                admin.getEmail(),
                admin.getMobile())
        ;
    }

    public LoginResultDto(AdminDetails adminDetails) {
        this(
                adminDetails.getRoleName(),
                adminDetails.getUsername(),
                adminDetails.getNickname(),
                adminDetails.getAvatar(),
                adminDetails.getEmail(),
                adminDetails.getMobile())
        ;
    }
}
