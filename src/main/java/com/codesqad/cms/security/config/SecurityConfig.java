package com.codesqad.cms.security.config;

import com.codesqad.cms.security.entrypoint.ApiAuthenticationEntryPoint;
import com.codesqad.cms.security.entrypoint.UIAuthenticationEntryPoint;
import com.codesqad.cms.security.filter.AuthoritiesLoggingAfterFilter;
import com.codesqad.cms.security.filter.AuthoritiesLoggingAtFilter;
import com.codesqad.cms.security.filter.CsrfCookieFilter;
import com.codesqad.cms.security.filter.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setExposedHeaders(List.of("Authorization"));
            config.setMaxAge(3600L);
            return config;
        })).csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers(getFreePaths())
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(getFreePaths()).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(authenticationProvider)
                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin
                        .loginPage("/web/login")
                        .loginProcessingUrl("/web/login")
                        .defaultSuccessUrl("/web/home", true)
                        .successHandler(myAuthenticationSuccessHandler())
                        .permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new UIAuthenticationEntryPoint("/web/login")))
                .logout(logout -> logout
                        .logoutUrl("/web/logout").permitAll()
                        .logoutSuccessUrl("/web/login?logout").permitAll()
                        .invalidateHttpSession(true)
                        .deleteCookies()
                )
                .httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint(new ApiAuthenticationEntryPoint("/api/v1/auth/login")))
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(accessDeniedHandler()));
        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getOutputStream().println("{ \"message\": \"Access Denied: Not enough privileges\" }");
        };
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
                String targetUrl = (String) request.getSession().getAttribute("url_prior_login");
                if (targetUrl != null) {
                    request.getSession().removeAttribute("url_prior_login");
                } else {
                    targetUrl = super.determineTargetUrl(request, response);
                }
                return targetUrl;
            }
        };
    }


    private String[] getFreePaths() {
        String[] freePaths = new String[9];
        freePaths[0] = "/api/v1/user/register";
        freePaths[1] = "/api/role/**";
        freePaths[2] = "/api/v1/auth/**";
        freePaths[3] = "/web/login";
        freePaths[4] = "/web/logout";
        freePaths[5] = "/api/v1/post/**";
        freePaths[6] = "/web/register";
        freePaths[7] = "/web/home";
        freePaths[8] = "/post/create";
        return freePaths;
    }
}
