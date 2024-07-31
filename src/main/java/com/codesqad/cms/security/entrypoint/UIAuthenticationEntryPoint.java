package com.codesqad.cms.security.entrypoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.io.IOException;
public class UIAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public UIAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException, IOException {
        String redirectUrl = null;

        if (request.getRequestURI().startsWith(request.getContextPath() + "/web/")) {
            redirectUrl = request.getRequestURI();
            if (request.getQueryString() != null) {
                redirectUrl += "?" + request.getQueryString();
            }
            request.getSession().setAttribute("url_prior_login", redirectUrl);
        }

        super.commence(request, response, authException);
    }
}