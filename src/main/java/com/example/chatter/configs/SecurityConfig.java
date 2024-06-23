package com.example.chatter.configs;

import com.example.chatter.technical.security.OtpAuthenticationProvider;
import com.example.chatter.technical.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OtpAuthenticationProvider otpAuthenticationProvider;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(OtpAuthenticationProvider otpAuthenticationProvider, CustomUserDetailsService userDetailsService) {
        this.otpAuthenticationProvider = otpAuthenticationProvider;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.ignoringRequestMatchers(
                        antMatcher(HttpMethod.POST, "/api/auth/send-otp"),
                        antMatcher(HttpMethod.POST, "/api/auth/verify-otp")
                ))
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                                antMatcher(HttpMethod.GET, "/js/**"),
                                antMatcher(HttpMethod.GET, "/css/**"),
                                antMatcher(HttpMethod.GET, "/media/**"),
                                antMatcher(HttpMethod.GET, "/webjars/**"),
                                antMatcher(HttpMethod.POST, "/api/auth/send-otp"),
                                antMatcher(HttpMethod.POST, "/api/auth/verify-otp"),
                                antMatcher(HttpMethod.GET, "/auth/verify"),
                                antMatcher(HttpMethod.GET, "/")
                        ).permitAll()
                        .anyRequest().authenticated())
                .authenticationProvider(otpAuthenticationProvider)
                .userDetailsService(userDetailsService)
                .formLogin(form -> form.loginPage("/auth").permitAll().defaultSuccessUrl("/", true).failureUrl("/auth?error=true"))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(((request, response, authException) -> {
                    if (!response.isCommitted()) {
                        if (request.getRequestURI().startsWith("/api")) {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                        } else {
                            response.sendRedirect(request.getContextPath() + "/auth");
                        }
                    }
                })));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(otpAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }


}
