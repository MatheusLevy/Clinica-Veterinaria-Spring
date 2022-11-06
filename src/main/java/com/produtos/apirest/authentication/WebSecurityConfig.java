package com.produtos.apirest.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/api/animal/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/area/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/appointment/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/owner/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/expertise/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/animalType/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/AppointmentType/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/user").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/user").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/user/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/user/feedback/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/user/username/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/user/authenticate").permitAll()
                .antMatchers("/api/veterinary/**").hasAnyRole("SECRETARIO", "ADMIN")
                .and()
                .csrf().disable();
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}