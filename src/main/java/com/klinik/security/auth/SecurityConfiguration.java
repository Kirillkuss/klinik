package com.klinik.security.auth;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.klinik.entity.Role;
import com.klinik.security.auth.handler.KlinikaAuthenticationSuccessHandler;
import com.klinik.security.auth.handler.KlinikaAuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final KlinikaAuthenticationFailureHandler klinikaAuthenticationFailureHandler;
    private final KlinikaAuthenticationSuccessHandler klinikaAuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests(requests -> requests
                .antMatchers("/login", "/change-password", "/logout", "/icon/", "/error", "/register", "/image-access-qr", "/code", "/clear-error-message").permitAll()
                .antMatchers("/web/swagger-ui/index.html", "/web/klinika", "/klinika").hasAnyRole("TEST")
                .antMatchers("/web/index.html", "/web", "/index", "/web/**", "/klinika", "/").hasAnyRole(Role.ADMIN.name(), Role.USER.name()) 
                .anyRequest().authenticated()) 
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/code") 
                        .defaultSuccessUrl("/web") 
                        .failureHandler(klinikaAuthenticationFailureHandler) 
                        .successHandler(klinikaAuthenticationSuccessHandler) 
                        .permitAll())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) 
                        .invalidSessionUrl("/code") 
                        .maximumSessions(1) 
                        .maxSessionsPreventsLogin(false))
                .logout(logout -> logout
                        .logoutUrl("/logout") 
                        .logoutSuccessUrl("/code?logout=true") 
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .csrf(csrf -> csrf.disable()) 
                .build();
    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent event) {
                event.getSession().setMaxInactiveInterval( 10 * 60 ); // 10 минут неактивности
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent event) {
                log.info("Session destroyed: " + event.getSession().getId());
            }
        };
    }
    /**
     * Генереация пароля через BCrypt 
     * 
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Генереация пароля через Argon2
     * 
     * saltLength  - длина соли
     * hashLength  - длина хэша
     * parallelism - кол-во потоков
     * memory      - количество памяти
     * iterations  - кол-во итераций
     * 
     * @return PasswordEncoder
     */
    /**@Bean
    public PasswordEncoder passwordEncoder() {
        int saltLength = 16;
        int hashLength = 32;
        int parallelism = 1;
        int memory  = 60000;
        int iterations = 10;
        return new Argon2PasswordEncoder( saltLength, hashLength, parallelism, memory, iterations );
    }*/

}
