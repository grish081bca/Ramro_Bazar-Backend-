package com.grish.RamroBazar.enums;

public enum RoleTypes {
    Admin("admin","has access every user"),
    User("user","buyers"),
    Seller("seller","product seller");

    private final String code;
    private final String description;

    RoleTypes(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
