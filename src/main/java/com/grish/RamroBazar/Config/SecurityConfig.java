package com.grish.RamroBazar.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //This method is knows as builder pattern in which you can use one object to call multiple methods
        http.csrf(customizer -> customizer.disable())//disable csrf
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())//enable authorization for every request
        .httpBasic(Customizer.withDefaults())//enable basic authentication
        .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));//create new session in every request
               // .build(); //You Can Direclty return http object but it is not recommended

        //http.formLogin(Customizer.withDefaults());//enable default login page


        return http.build();
    }
}
