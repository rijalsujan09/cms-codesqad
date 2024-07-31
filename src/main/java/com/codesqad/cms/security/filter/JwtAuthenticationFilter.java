package com.codesqad.cms.security.filter;

import com.codesqad.cms.security.constant.SecurityConstants;
import com.codesqad.cms.security.service.JwtService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(SecurityConstants.JWT_HEADER);
        final String jwt;
        final String userEmail;

        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = this.jwtService.extractUsername(jwt);
        logger.info("JWT Authentication Filter In-progress: " + userEmail);
        if (!StringUtils.isBlank(userEmail) || SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                System.out.println(userDetails.getAuthorities().toString());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<String> freePaths = getFreePaths();
        String servletPath = request.getServletPath();
        return freePaths.contains(servletPath);
    }

    private List<String> getFreePaths() {
        List<String> freePaths = new ArrayList<>();
        freePaths.add("/api/v1/user/register");
        freePaths.add("/api/role/**");
        freePaths.add("/api/v1/auth/**");
        freePaths.add("/web/login");
        freePaths.add("/api/v1/post/**");
        freePaths.add("/web/register");
        return freePaths;
    }

}
