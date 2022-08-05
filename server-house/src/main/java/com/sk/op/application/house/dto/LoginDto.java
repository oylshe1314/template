package com.sk.op.application.house.dto;

import com.sk.op.application.common.validation.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "登录信息")
public class LoginDto {

    @NullOrNotBlank
    @Schema(description = "账号")
    private String account;

    @NullOrNotBlank
    @Schema(description = "密码")
    private String password;

    @NullOrNotBlank
    @Schema(description = "短信验证码")
    private String smsCode;

    @NullOrNotBlank
    @Schema(description = "微信登录代码")
    private String wechatCode;
}
