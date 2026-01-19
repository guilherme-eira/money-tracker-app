package io.github.guilherme_eira.money_tracker_app.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${app.security.remember-me-key}")
    private String rememberMeKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/css/**", "/js/**", "/assets/**", "/", "/index", "/home", "/user/create", "/auth/**").permitAll();
                    req.anyRequest().authenticated();
                }
                )
                .formLogin(form -> form.loginPage("/auth/login").defaultSuccessUrl("/overview", true).permitAll())
                .logout(logout -> logout.permitAll().deleteCookies("JSESSIONID", "remember-me"))
                .rememberMe(rememberMe -> rememberMe.key(rememberMeKey))
                .build();
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
