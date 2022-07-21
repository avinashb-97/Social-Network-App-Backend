package com.qmul.Social.Network.utils;

import com.qmul.Social.Network.model.persistence.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

public class AuthUtil {

    private static Authentication getLoggedInUser()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getLoggedInUserName()
    {
        return getLoggedInUser().getName();
    }

    private static Role getCurrentUserRole()
    {
        Collection roles = getLoggedInUser().getAuthorities();
        return getUserRole(roles);
    }

    public static Role getUserRole(Collection roles)
    {
        if(roles.contains(new SimpleGrantedAuthority(Role.ADMIN.toString())))
        {
            return Role.ADMIN;
        }
        if(roles.contains(new SimpleGrantedAuthority(Role.STAFF.toString())))
        {
            return Role.STAFF;
        }
        if (roles.contains(new SimpleGrantedAuthority(Role.USER.toString())))
        {
            return Role.USER;
        }

        if (roles.contains(new SimpleGrantedAuthority(Role.RECRUITER.toString())))
        {
            return Role.RECRUITER;
        }
        return null;
    }

    public static boolean isCurrentUserIsAdmin()
    {
        return getCurrentUserRole() == Role.ADMIN;
    }

    public static boolean isCurrentUserIsStaff()
    {
        return getCurrentUserRole() == Role.STAFF;
    }

    public static boolean isCurrentUserIsUser()
    {
        return getCurrentUserRole() == Role.USER;
    }

    public static String getBaseUrl()
    {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }

}
