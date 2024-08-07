package com.bashkir777.commands;

public enum CommandType {
    ADD("add"), REMOVE("remove"), PUSH("push"),
    HELP("help"), INFO("info"),
    PRINT("print"), SWITCH_MODE("switch_mode");

    private final String name;

    CommandType(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
