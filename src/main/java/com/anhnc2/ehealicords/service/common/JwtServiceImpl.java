package com.anhnc2.ehealicords.service.common;

import com.anhnc2.ehealicords.config.JwtConfig;
import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.auth.AuthUser;
import com.anhnc2.ehealicords.data.auth.JwtAuthUser;
import com.anhnc2.ehealicords.exception.AuthException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;

@Service
public class JwtServiceImpl implements JwtService {
    public static final String FULL_NAME = "fullName";
    public static final String USERNAME = "username";
    public static final String ROLES = "roles";

    private final Algorithm algorithm;
    private final long expireTime;

    public JwtServiceImpl(JwtConfig jwtConfig) {
        jwtConfig.validate();
        algorithm = Algorithm.HMAC512(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
        expireTime = jwtConfig.getValidTime();
    }

    @Override
    public String generate(AuthUser authUser) {
        return JWT.create()
                .withSubject(authUser.getId().toString())
                .withClaim(FULL_NAME, authUser.getFullName())
                .withClaim(USERNAME, authUser.getUsername())
                .withArrayClaim(ROLES, authUser.getListStringOfRoles())
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .sign(algorithm);
    }

    @Override
    public AuthUser parse(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);

            return JwtAuthUser.builder()
                    .id(Long.parseLong(jwt.getSubject()))
                    .fullName(jwt.getClaim(FULL_NAME).asString())
                    .username(jwt.getClaim(USERNAME).asString())
                    .roles(new HashSet<>(jwt.getClaim(ROLES).asList(String.class)))
                    .build();
        } catch (Exception e) {
            throw new AuthException(StatusCode.INVALID_TOKEN, e);
        }
    }
}
