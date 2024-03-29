package com.bdt.queswer.security.jwt;

import com.bdt.queswer.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
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

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String loginUrl) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(loginUrl);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        try {
            //System.out.println("authen");
            if (request.getMethod().equals("OPTIONS")) {
                return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                "",
                                ""
                        )
                );
            }
            else {
                Account account = new ObjectMapper().readValue(request.getInputStream(), Account.class);
                return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                account.getEmail(),
                                account.getPassword()
                        )
                );
            }
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
        claims.put("roles", roles);
        //System.out.println("Claims mà authentication đóng gói: "+claims);
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods","GET,HEAD,POST");
        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type");
        response.setHeader("Access-Control-Max-Age","1800");
        response.setHeader("Vary","Origin");
        response.addHeader("Vary","Access-Control-Request-Method");
        response.addHeader("Vary","Access-Control-Request-Headers");
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        //response.addHeader("Access-Control-Allow-Origin", "*");
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpStatus.OK.value());
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods","GET,HEAD,POST");
            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type");
            response.setHeader("Access-Control-Max-Age","1800");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Vary","Origin");
            response.addHeader("Vary","Access-Control-Request-Method");
            response.addHeader("Vary","Access-Control-Request-Headers");
        } else {
            super.unsuccessfulAuthentication(request,response,failed);
        }
    }


}
