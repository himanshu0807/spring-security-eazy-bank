package com.eazybank.springsecurityeazybank.config;

import com.eazybank.springsecurityeazybank.filter.AuthoritiesLoggingAtFilter;
import com.eazybank.springsecurityeazybank.filter.AuthoritiesLoggingFilter;
import com.eazybank.springsecurityeazybank.filter.CsrfCookieFilter;
import com.eazybank.springsecurityeazybank.filter.JWTTokenGeneratorFilter;
import com.eazybank.springsecurityeazybank.filter.JWTTokenValidatorFilter;
import com.eazybank.springsecurityeazybank.filter.RequestValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        /* Here the CORS issue is resolved by allowing the cors for the origin globally in spring security, so when the preflight request
         * is received from the browser it allows it and send the Allow-origin-allow-headers in the response.
         * Another not so ok way is using @CrossOrigin on the controller in backend
         */
        /* now we need to explicitly configure if we do not want to save the user detail to security context */
        /* we want our spring security not to generate any JSession Id or http session */
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .securityContext(sc -> sc.requireExplicitSave(false))
//                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
            .cors(c -> c.configurationSource(request -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
                corsConfiguration.setMaxAge(3600L);
                return corsConfiguration;
            }))
            /* disabling the csrf to hit from the postman
             * allowing the csrf skip for the public endpoint
             * create a csrf token
             * create a csrf filter that add the token for every request after the authentication*/
            // .csrf(AbstractHttpConfigurer::disable)
            .csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)
                              .ignoringRequestMatchers("/contact", "/register")
                              .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
            // commenting out as we will be using the keycloak to get token
            /*.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class)
            .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
            .addFilterAfter(new AuthoritiesLoggingFilter(), BasicAuthenticationFilter.class)
            .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)*/
            .authorizeHttpRequests(
                    (requests) -> requests.requestMatchers("/myAccount").hasRole("USER")
                                          .requestMatchers("myBalance").hasAnyRole("USER", "ADMIN")
                                          .requestMatchers("/myLoans").authenticated() //for method level authZ
                                          .requestMatchers("/myCards").hasRole("USER")
                                          .requestMatchers("/user").authenticated()
                                          .requestMatchers("/notices", "/contact", "/register").permitAll());
        /*http.formLogin(withDefaults());
        http.httpBasic(withDefaults());*/
        // to use oauth authN
        http.oauth2ResourceServer(r -> r.jwt(j -> j.jwtAuthenticationConverter(jwtAuthenticationConverter)));
        return http.build();
    }
    /**
     * Password encoder bean to encode the password using the BCrypt
     * example value - "$2a$10$vqyURisnOEBr0vWHDMpCrOfNm8hrWG1P9gwXU1dvMpJkS3D/pFT.6"
     * the first three character indicate the version of BCrypt, the next three character indicate the workload factor, remaining is the
     * hash value
     *
     * @return reference object of interface
     * not required as we be using the keycloak
     */
    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

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

}
