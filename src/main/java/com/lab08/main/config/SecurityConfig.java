package com.lab08.main.config;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import com.lab08.main.Entity.Account;
import com.lab08.main.service.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoderConfig pe;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            try {
                Account user = accountService.findById(username);
                String password = pe.bCryptPasswordEncoder().encode(user.getPassword());
                String[] roles = user.getAuthorities().stream()
                        .map(authorities -> authorities.getRole().getId())
                        .collect(Collectors.toList())
                        .toArray(new String[0]);
                return User.withUsername(username).password(password).roles(roles).build();
            } catch (NoSuchElementException e) {
                throw new UsernameNotFoundException(username + " not found");
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/order/**").authenticated()
                        .requestMatchers("/admin/**").hasAnyRole("STAF", "DIRE")
                        .requestMatchers("/rest/authorities").hasRole("DIRE")
                        .requestMatchers("/**").permitAll())
                .formLogin(form -> form
                        .loginPage("/security/login/form")
                        .loginProcessingUrl("/security/login")
                        .defaultSuccessUrl("/", false)
                        .failureUrl("/security/login/error"))
                .rememberMe(remember -> remember
                        .tokenValiditySeconds(86400))
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/security/unauthorized"))
                .logout(logout -> logout
                        .logoutUrl("/security/logoff")
                        .logoutSuccessUrl("/security/logoff/success"));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(HttpMethod.OPTIONS, "/**");
    }
}
