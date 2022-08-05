package com.sk.op.application.entity.entity;

public enum MenuType {

    directory(1, "目录"),

    menu(2, "菜单"),

    api(3, "接口"),
    ;
    private final int value;
    private final String desc;

    MenuType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int value() {
        return value;
    }

    public String desc() {
        return this.desc;
    }

    public static MenuType valueOf(int value) {
        for (MenuType state : MenuType.values()) {
            if (state.value == value) {
                return state;
            }
        }
        return null;
    }
}
