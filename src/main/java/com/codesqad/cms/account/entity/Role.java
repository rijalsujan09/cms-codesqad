package com.codesqad.cms.account.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    USER("user"),
    MODERATOR("moderator"),
    ADMIN("admin"),
    SUPER_ADMIN("super_admin");
    private final String type;

    Role(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
    @JsonCreator
    public static Role getRole(String givenType){
        for(Role role : values()){
            if(role.type.equalsIgnoreCase(givenType)){
                return role;
            }
        }
        throw new RuntimeException(String.format("Role Constant Not found for %s", givenType));
    }
}
