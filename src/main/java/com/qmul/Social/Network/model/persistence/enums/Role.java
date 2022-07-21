package com.qmul.Social.Network.model.persistence.enums;


import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN,
    STAFF,
    RECRUITER,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}