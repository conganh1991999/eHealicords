package com.anhnc2.ehealicords.service.common;

import com.anhnc2.ehealicords.data.auth.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {
    @Override
    public AuthUser getCurrentUser() {
        return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
