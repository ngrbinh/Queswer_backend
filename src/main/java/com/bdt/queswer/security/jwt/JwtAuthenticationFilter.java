package com.bdt.queswer.security.jwt;

import com.bdt.queswer.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.bdt.queswer.security.Constants.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,String loginUrl) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(loginUrl);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        try {
            Account account = new ObjectMapper().readValue(request.getInputStream(),Account.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            account.getEmail(),
                            account.getPassword()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        //System.out.println("Authorities mà authentication nhận được");
        ArrayList<String> roles = new ArrayList<>();
        user.getAuthorities().forEach(authority -> {
            //System.out.println(authority);
            roles.add(authority.getAuthority());
        });
        claims.put("roles",roles);
        //System.out.println("Claims mà authentication đóng gói: "+claims);
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.addHeader("Access-Control-Expose-Headers","Authorization");
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        //super.unsuccessfulAuthentication(request, response, failed);
        response.addHeader("Access-Control-Allow-Origin","*");
    }


}
