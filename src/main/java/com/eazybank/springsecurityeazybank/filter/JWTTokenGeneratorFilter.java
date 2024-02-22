package com.eazybank.springsecurityeazybank.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.eazybank.springsecurityeazybank.filter.SecurityConstant.JWT_HEADER;
import static com.eazybank.springsecurityeazybank.filter.SecurityConstant.JWT_KEY;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {


    /**
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this filter to pass the request and response
     *                 to for further processing
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecretKey secretKey = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder().issuer("Eazy Bank").subject("JWT Token")
                             .claim("username", authentication.getName())
                             .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                             .issuedAt(new Date())
                             .expiration(new Date(new Date().getTime() + 3000000))
                             .signWith(secretKey).compact();
            response.setHeader(JWT_HEADER, jwt);
        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/user");
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}
