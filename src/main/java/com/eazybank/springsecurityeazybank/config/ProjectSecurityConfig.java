package com.eazybank.springsecurityeazybank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        /* now we need to explicitly configure if we do not want to save the user detail to security context */
        http.securityContext(sc -> sc.requireExplicitSave(false))
            /* disabling the csrf to hit from the postman*/
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                    (requests) -> requests.requestMatchers("/myAccount", "/myBalance", "/myLoans", "myCards").authenticated()
                                          .requestMatchers("/notices", "/contact", "/register").permitAll());
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
    /*@Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }*/

    /**
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
