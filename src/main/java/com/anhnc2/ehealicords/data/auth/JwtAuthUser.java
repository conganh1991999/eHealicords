package com.anhnc2.ehealicords.data.auth;

import com.anhnc2.ehealicords.constant.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@ToString
public class JwtAuthUser implements AuthUser {
    private Long id;
    private String username;
    private String fullName;
    private Set<String> roles;

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public UserStatus getStatus() {
        return null;
    }

    @JsonIgnore
    @Override
    public String[] getListStringOfRoles() {
        return roles.toArray(new String[] {});
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthority() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
