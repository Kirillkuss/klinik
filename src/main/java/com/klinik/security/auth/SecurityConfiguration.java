package com.klinik.security.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.klinik.entity.Role;
import com.klinik.security.auth.handler.KlinikaAuthenticationSuccessHandler;
import com.klinik.security.auth.handler.KlinikaAuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final KlinikaAuthenticationFailureHandler klinikaAuthenticationFailureHandler;
    private final KlinikaAuthenticationSuccessHandler klinikaAuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests(requests -> requests
                .antMatchers("/login", "/klinika", "/swagger-ui/**", "/api/**", "/web/**")
                .hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                .anyRequest()
                .authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/web")
                        .failureHandler( klinikaAuthenticationFailureHandler )
                        .successHandler( klinikaAuthenticationSuccessHandler )
                        .permitAll())
                        .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                            .invalidSessionUrl("/login")
                            .maximumSessions( 10 )) //300
                        .logout(logout -> logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/web/login?logout=true")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID"))
                            .csrf(csrf -> csrf.disable())
                            .build();
        
        /**return http.authorizeRequests(requests -> requests
                        .antMatchers("/")
                        .hasAnyRole( Role.ADMIN.name(), Role.USER.name())
                        .anyRequest()
                        .authenticated())
                            .formLogin( login -> login.permitAll() )
                            .logout( logout -> logout.permitAll() )
                            .csrf( csrf -> csrf.disable() )
                            .build();*/
         
        /**return http.authorizeHttpRequests( request -> request.antMatchers("/")
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
