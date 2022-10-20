package com.produtos.apirest.autenticacao;

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
                .antMatchers("/api/consulta/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/dono/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/especialidade/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/tipoAnimal/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/tipoConsulta/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/usuario/salvar").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/usuario/atualizar").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/usuario/remover/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/usuario/remover/feedback/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/usuario/buscar/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers("/api/usuario/buscarPorUsername/**").hasAnyRole("SECRETARIO", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/usuario/autenticar").permitAll()
                .antMatchers("/api/veterinario/**").hasAnyRole("SECRETARIO", "ADMIN")
                .and()
                .csrf().disable();
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}