package com.anhnc2.ehealicords.service.common;

import com.anhnc2.ehealicords.data.auth.AuthUser;

public interface AppUserService {
    /** Only use this method in authenticated context */
    AuthUser getCurrentUser();

    /** Only use this method in authenticated context */
    long getCurrentUserId();
}
