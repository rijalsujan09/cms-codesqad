package com.codesqad.cms.security.filter;

import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthoritiesLoggingAtFilter implements Filter {

    private final Logger logger  = LoggerFactory.getLogger(AuthoritiesLoggingAtFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("Authentication Validation is In-progress => At Filter");
        chain.doFilter(request, response);
    }
}
