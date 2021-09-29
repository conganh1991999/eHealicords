package com.anhnc2.ehealicords.data.auth;

import java.util.Collection;

import com.anhnc2.ehealicords.constant.UserStatus;
import org.springframework.security.core.GrantedAuthority;

public interface AuthUser {
    String getUsername();

    String getFullName();

    Long getId();

    String getPassword();

    UserStatus getStatus();

    String[] getListStringOfRoles();

    Collection<GrantedAuthority> getAuthority();
}
