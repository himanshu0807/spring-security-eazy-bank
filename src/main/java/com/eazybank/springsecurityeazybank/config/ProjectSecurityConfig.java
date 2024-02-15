package com.eazybank.springsecurityeazybank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (requests) -> requests.requestMatchers("/myAccount", "/myBalance", "/myLoans", "myCards").authenticated()
                                      .requestMatchers("/notices", "/contact").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    /*@Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        // withDefaultPassword encoder
        *//*UserDetails admin = User.withDefaultPasswordEncoder()
                                .username("admin")
                                .password("admin123")
                                .authorities("admin")
                                .build();
        UserDetails user = User.withDefaultPasswordEncoder()
                                .username("user")
                                .password("12345")
                                .authorities("read")
                                .build();*//*
        // with password encoder NoOpPasswordEncoder though it give plain text
        UserDetails admin = User.withUsername("admin")
                                .password("admin123")
                                .authorities("admin")
                                .build();
        UserDetails user = User.withUsername("user")
                               .password("12345")
                               .authorities("read")
                               .build();
        return new InMemoryUserDetailsManager(admin,user);
    }*/

    /**
     *  JDBCUserDetailsManager where we need the restricted schema structure in production env.
     * @param dataSource
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    /**
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
