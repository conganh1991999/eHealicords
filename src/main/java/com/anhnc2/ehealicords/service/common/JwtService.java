package com.anhnc2.ehealicords.service.common;

import com.anhnc2.ehealicords.data.auth.AuthUser;

public interface JwtService {
    String generate(AuthUser authUser);

    AuthUser parse(String token);
}
