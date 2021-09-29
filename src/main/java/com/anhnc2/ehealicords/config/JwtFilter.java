package com.anhnc2.ehealicords.config;

import com.anhnc2.ehealicords.data.auth.AuthUser;
import com.anhnc2.ehealicords.service.common.JwtService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain)
            throws ServletException, IOException {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null || token.isEmpty()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        try {
            AuthUser authUser = jwtService.parse(token);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthority());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            LOGGER.warn("Error when handle authentication", e);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
