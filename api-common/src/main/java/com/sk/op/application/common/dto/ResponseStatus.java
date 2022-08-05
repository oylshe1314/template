package com.sk.op.application.common.dto;

import lombok.Getter;

@Getter
public enum ResponseStatus {

    //成功代码
    SUCCESSFUL(200, "成功"),

    //客户端错误
    FAILED(4000, ""),
    NOT_LOGGED(4001, "请先登录"),
    ACCESS_DENIED(4002, "权限不足"),
    TOKEN_EXPIRED(4003, "TOKEN已过期"),
    TOKEN_INVALID(4004, "无效的TOKEN"),
    METHOD_ERROR(4005, "方法错误"),
    PARAMETER_ERROR(4006, "参数错误"),

    //服务端错误
    UNKNOWN(5000, "未知错误"),
    ;

    private final int status;

    private final String message;

    ResponseStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ResponseStatus get(int status) {
        for (ResponseStatus value : ResponseStatus.values()) {
            if (value.getStatus() == status) {
                return value;
            }
        }
        return ResponseStatus.UNKNOWN;
    }
}
