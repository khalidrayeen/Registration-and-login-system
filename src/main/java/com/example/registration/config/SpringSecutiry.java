package com.example.registration.config;


import com.example.registration.repository.UserRepository;
import com.example.registration.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SpringSecutiry {

    @Autowired
    public UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth ->
                    auth.requestMatchers("/register/**").permitAll()
                            .requestMatchers("/index/**").permitAll()
                            .requestMatchers("/users").hasRole("ADMIN")
            )
            .formLogin(form ->
                    form
                            .loginPage("/login")
                            .loginProcessingUrl("/login")
                            .defaultSuccessUrl("/users")
                            .permitAll()
            ).logout(
                    logout->logout
                            .logoutUrl("/logout")
                            .permitAll()
            );

    return http.build();
}


@Autowired
public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {

    builder.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
}



}
