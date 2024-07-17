package com.klinik.security;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;

@Configuration
@PropertySource("application-google.properties")
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";

    private final  Environment env;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests( requests -> requests
                    .antMatchers( "/login", "/loginFailure", "/" )
                    .permitAll()
                    .anyRequest()
                    .authenticated())
                    .csrf( AbstractHttpConfigurer::disable )
                    .oauth2Login( login -> login
                        .loginPage("/login")
                        .authorizationEndpoint()
                        .baseUri("/oauth2/authorize-client")
                        .authorizationRequestRepository( authorizationRequestRepository() )
                        .and()
                        .tokenEndpoint()
                        .accessTokenResponseClient( accessTokenResponseClient() )
                        .and()
                        .defaultSuccessUrl("/web")
                        .failureUrl("/loginFailure")).build();
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        return accessTokenResponseClient;
    }

    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository( Arrays.asList("google")
                                                               .stream()
                                                               .map( c -> getRegistration( c ))
                                                               .filter( registration -> registration != null )
                                                               .collect( Collectors.toList() ));
    }

    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    private ClientRegistration getRegistration(String client) {
        String clientId = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-id");
        if (clientId == null) {
            return null;
        }
        String clientSecret = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-secret");
        if (client.equals("google")) {
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
        }
        return null;
    }


}
