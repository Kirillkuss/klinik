package com.klinik.security;
 
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@PropertySource("application-google-github.properties")
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";
    //private static String CLIENT_ID = ".client-id";
    //private static String CLIENT_SECRET = ".client-secret";

    private final Environment env;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests( requests -> requests
        .requestMatchers( "/login", "/loginFailure", "/" )
        .permitAll()
        .anyRequest()
        .authenticated())
        .csrf( AbstractHttpConfigurer::disable )
        .oauth2Login( login -> login
            .loginPage("/login")
            .authorizationEndpoint( s -> s.baseUri("/oauth2/authorize-client")
                .authorizationRequestRepository(authorizationRequestRepository()))
                .tokenEndpoint( t ->t.accessTokenResponseClient(accessTokenResponseClient()))
                .defaultSuccessUrl("/klinika")
                .failureHandler((request, response, exception) -> {
                    exception.printStackTrace( System.err );
                    response.sendRedirect("/loginFailure");
                })).build();
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
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository( Arrays.asList("google", "github", "facebook")
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

        if (client.equals("github")) {
            return CommonOAuth2Provider.GITHUB.getBuilder(client)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
        }
        if (client.equals("facebook")) {
            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
        }
        return null;
    }
}
/**
    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        return accessTokenResponseClient;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = Arrays.asList(googleClientRegistration(), mailClientRegistration(), githubClientRegistration(), facebookClientRegistration());
        return new InMemoryClientRegistrationRepository(registrations);
    }

    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }
    
    private ClientRegistration googleClientRegistration() {
        String google = "google";
       return CommonOAuth2Provider.GOOGLE.getBuilder( google )
             .clientId(env.getProperty( CLIENT_PROPERTY_KEY + google +CLIENT_ID))
            .clientSecret(env.getProperty( CLIENT_PROPERTY_KEY + google +CLIENT_SECRET))
            .build();
    }
    
    private ClientRegistration githubClientRegistration() {
        String github = "github";
        return CommonOAuth2Provider.GITHUB.getBuilder(github)
        .clientId(env.getProperty( CLIENT_PROPERTY_KEY + github +CLIENT_ID))
        .clientSecret(env.getProperty( CLIENT_PROPERTY_KEY + github +CLIENT_SECRET))
            .build();
    }

    private ClientRegistration facebookClientRegistration() {
        String facebook ="facebook";
        return CommonOAuth2Provider.FACEBOOK.getBuilder(facebook)
        .clientId(env.getProperty( CLIENT_PROPERTY_KEY + facebook +CLIENT_ID))
        .clientSecret(env.getProperty( CLIENT_PROPERTY_KEY + facebook +CLIENT_SECRET))
            .build();
    }


    private ClientRegistration mailClientRegistration() {
        return ClientRegistration.withRegistrationId("Mail")
                                .clientId("fd2a54869ccb4957979dfdfba68f7e6f")
                                .clientSecret("2d64faf6859c42ff930df511a4f9bb78")
                                .redirectUri( "http://localhost:8082/login/oauth2/code/mail")
                                .tokenUri("https://oauth.mail.ru/token")
                                .authorizationUri("https://oauth.mail.ru/login")
                                .userInfoUri("https://api.mail.ru/my/")
                                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) 
                                .userNameAttributeName( "id")
                                .scope("user_info", "email")
                                .build();

    } */






