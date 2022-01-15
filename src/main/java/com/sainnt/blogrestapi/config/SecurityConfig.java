package com.sainnt.blogrestapi.config;

import com.sainnt.blogrestapi.security.JwtAuthenticationEntryPoint;
import com.sainnt.blogrestapi.security.JwtAuthenticationFilter;
import com.sainnt.blogrestapi.security.JwtTokenProvider;
import com.sainnt.blogrestapi.security.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailService userDetailService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public JwtAuthenticationFilter authenticationFilter(){
        return new JwtAuthenticationFilter(jwtTokenProvider,userDetailService);
    }

    @Autowired
    public SecurityConfig(UserDetailService userDetailService, JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtTokenProvider jwtTokenProvider) {
        this.userDetailService = userDetailService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest()
                .authenticated();
        http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
