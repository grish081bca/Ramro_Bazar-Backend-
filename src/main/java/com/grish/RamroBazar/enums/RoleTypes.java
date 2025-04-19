package com.grish.RamroBazar.enums;

public enum RoleTypes {
    Super_Admin("Super_admin","has access to the super function"),
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
