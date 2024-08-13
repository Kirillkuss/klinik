package com.klinik.security.auth;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import com.klinik.security.generate.GenerateKeysDataBase;
import com.klinik.security.generate.GenerateKeystore;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final GenerateKeystore generateKeystore = new GenerateKeystore();
    private final GenerateKeysDataBase generateKeysDataBase;
    /**
     * for docker-compose
     */
    //@Value("${JWT_PUBLIC_KEY}")
    /**
     * Из фалов pem
     */
    @Value("${jwt.public.key}")
    RSAPublicKey publicKeyPem;

    /**
     * for docker-compose
     */
    //@Value("${JWT_PRIVATE_KEY}")
    @Value("${jwt.private.key}")
    RSAPrivateKey privateKeyPem; 
    /**
     * из хранилища keystore
     */
    RSAPublicKey publicKey = generateKeystore.getPublicKey().orElseThrow();
    RSAPrivateKey privateKey = generateKeystore.getPrivateKey().orElseThrow();

    @Bean
    public SecurityFilterChain filterChain( HttpSecurity httpSecurity ) throws Exception{
        return httpSecurity.authorizeHttpRequests(request -> request
                            .antMatchers( "/auth/**",  "/swagger-ui/**","/swagger-ui/index.html", "/api/**", "/" )
                            .permitAll()
                            .anyRequest()
                            .authenticated())
                            .csrf(csrf -> csrf
                                .disable())
                                .sessionManagement( management -> management
                                    .sessionCreationPolicy( SessionCreationPolicy.STATELESS ))
                                    .oauth2ResourceServer( OAuth2ResourceServerConfigurer::jwt )
                                    .exceptionHandling(ex -> ex
                                        .authenticationEntryPoint( new BearerTokenAuthenticationEntryPoint() )
                                        .accessDeniedHandler( new BearerTokenAccessDeniedHandler() )
                                        .and())
                                        .build();
    }
    // Через RSAPublicKey and RSAPrivateKey pem файлы
    /**@Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey( this.publicKey ).build();
    }

    @Bean
    JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder( this.publicKey ).privateKey( this.privateKey ).build();
        return new NimbusJwtEncoder( new ImmutableJWKSet<>( new JWKSet( jwk )));
    }*/

    /// Через KeyStore
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey( this.publicKey ).build();
    }

    @Bean
    JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder( this.publicKey ).privateKey( this.privateKey ).build();
        return new NimbusJwtEncoder( new ImmutableJWKSet<>( new JWKSet( jwk )));
    }

    // Черезе DataBase
    /**@Bean
    JwtDecoder jwtDecoder() throws Exception {
        return NimbusJwtDecoder.withPublicKey( generateKeysDataBase.getRSAPublicKey().orElseThrow() ).build();
    }

    @Bean
    JwtEncoder jwtEncoder()  throws Exception{
        JWK jwk = new RSAKey.Builder( generateKeysDataBase.getRSAPublicKey().orElseThrow() )
                            .privateKey( generateKeysDataBase.getRSAPrivateKey().orElseThrow() )
                            .build();
        return new NimbusJwtEncoder( new ImmutableJWKSet<>( new JWKSet( jwk )));
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
