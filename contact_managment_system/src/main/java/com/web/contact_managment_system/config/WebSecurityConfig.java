package com.web.contact_managment_system.config;


import com.web.contact_managment_system.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers( "/user/login", "/user/register")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST,"/user/register", "/user/login")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
//                        .permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/user/login")
                        .usernameParameter("username")
                        .defaultSuccessUrl("/contact/all")
                        .loginProcessingUrl("/user/login")
                        .failureUrl("/user/login?error=true")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
//
    private CustomUserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService,
                             PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder);
    }

}