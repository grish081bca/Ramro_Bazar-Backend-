package com.grish.RamroBazar.enums;

public enum RoleTypes {
    ADMIN("Admin","has access every user"),
    USER("User","buyers"),
    SELLER("Seller","product seller");

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
