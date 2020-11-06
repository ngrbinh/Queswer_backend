package com.bdt.queswer.security;

import com.bdt.queswer.security.jwt.JwtAuthenticationFilter;
import com.bdt.queswer.security.jwt.JwtAuthorizationFilter;
import com.bdt.queswer.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.bdt.queswer.security.Constants.*;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                //.antMatchers(HttpMethod.POST,"").permitAll()
                .antMatchers(HttpMethod.DELETE,"/user/{\\d+}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,GET_PERMIT_ALL).permitAll()
                .antMatchers(HttpMethod.POST,POST_PERMIT_ALL).permitAll()
                .anyRequest().authenticated();
        http
                .addFilter(new JwtAuthenticationFilter(authenticationManager(),"/login"))
                .addFilter (new JwtAuthorizationFilter(authenticationManager()))
                //.addFilterAfter(new CustomFilter(),JwtAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
