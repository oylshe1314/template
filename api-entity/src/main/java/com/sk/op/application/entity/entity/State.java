package com.sk.op.application.entity.entity;

public enum State {

    disable(0, "禁用"),

    enable(1, "启用"),
    ;
    private final int value;
    private final String desc;

    State(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int value() {
        return value;
    }

    public String desc() {
        return this.desc;
    }

    public static State valueOf(int value) {
        for (State state : State.values()) {
            if (state.value == value) {
                return state;
            }
        }
        return null;
    }
}
