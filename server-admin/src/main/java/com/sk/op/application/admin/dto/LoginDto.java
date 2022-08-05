package com.sk.op.application.admin.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Schema(description = "登录信息")
public class LoginDto {

    @NotBlank
    @Schema(description = "用户名")
    private final String username;

    @NotBlank
    @Schema(description = "密码")
    private final String password;

    @JsonCreator
    public LoginDto(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }
}
