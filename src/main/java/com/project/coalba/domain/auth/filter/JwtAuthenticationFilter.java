package com.project.coalba.domain.auth.filter;

import com.project.coalba.domain.auth.token.AuthTokenManager;
import com.project.coalba.domain.auth.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthTokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = HeaderUtil.getAccessToken(request);
        if (tokenManager.validate(token)) {
            Authentication authentication = tokenManager.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication); //인증된 사용자 정보 Security Context에 저장
        }

        filterChain.doFilter(request, response);
    }
}
