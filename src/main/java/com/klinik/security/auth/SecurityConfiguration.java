package com.klinik.security.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.klinik.entity.Role;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests(requests -> requests
                        .antMatchers("/**")
                        .hasAnyRole( Role.ADMIN.name(), Role.USER.name())
                        .anyRequest()
                        .authenticated())
                            .formLogin( login -> login.permitAll() )
                            .logout( logout -> logout.permitAll() )
                            .csrf( csrf -> csrf.disable() )
                            .build();

        /**return http.authorizeHttpRequests( request -> request.antMatchers("/**")
                        .hasAnyRole( Role.ADMIN.name(), Role.USER.name())
                        .anyRequest()
                        .authenticated())
                        .httpBasic(Customizer.withDefaults())
                        .build();*/
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
