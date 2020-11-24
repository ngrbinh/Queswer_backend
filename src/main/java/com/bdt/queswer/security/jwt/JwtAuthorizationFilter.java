package com.bdt.queswer.security.jwt;

import com.bdt.queswer.service.MyUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.bdt.queswer.security.Constants.*;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;


    public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(HEADER_STRING);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods","*");
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, " +
                "Origin,Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request,response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX,""))
                    .getBody();
            //System.out.println("Claims mà authorization giải được: "+claims);
            String user = claims.getSubject();
            ArrayList<String> rawAuthorites = (ArrayList<String>) claims.get("roles");
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            for (String rawAuthorite : rawAuthorites) {
                GrantedAuthority authority = new SimpleGrantedAuthority(rawAuthorite);
                authorities.add(authority);
            }
            //System.out.println(authorities);
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user,null,
                        authorities);
            }
            return null;
        }
        return null;
    }
}
