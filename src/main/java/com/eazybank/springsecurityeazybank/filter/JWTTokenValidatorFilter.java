package com.eazybank.springsecurityeazybank.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.eazybank.springsecurityeazybank.filter.SecurityConstant.JWT_HEADER;
import static com.eazybank.springsecurityeazybank.filter.SecurityConstant.JWT_KEY;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(JWT_HEADER);
        if (jwt != null) {
            try {
                SecretKey secretKey = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parser()
                                    .verifyWith(secretKey)
                                    .build()
                                    .parseSignedClaims(jwt)
                                    .getPayload();
                String username = (String) claims.get("username");
                String authorities = (String) claims.get("authorities");
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(username, null,
                                                                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new BadRequestException("Invalid jwt token!");
            }
        }
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/user");
    }
}
