package com.klinik.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPISecurityConfig {

    @Value("${keycloak.auth-server-url}")
    String authServerUrl;
    @Value("${keycloak.realm}")
    String realm;

    private static final String OAUTH_SCHEME_NAME = "Klinika_security_schema";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components( new Components().addSecuritySchemes( OAUTH_SCHEME_NAME, createOAuthScheme() ))
                            .addSecurityItem( new SecurityRequirement().addList( OAUTH_SCHEME_NAME ));
    }

    private SecurityScheme createOAuthScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.OAUTH2)
                                   .flows( createOAuthFlows() );
    }

    private OAuthFlows createOAuthFlows() {
        return new OAuthFlows().implicit( createAuthorizationCodeFlow() );
    }

    private OAuthFlow createAuthorizationCodeFlow() {
        return new OAuthFlow().authorizationUrl( authServerUrl + "/realms/" + realm + "/protocol/openid-connect/auth" )
                              .scopes( new Scopes().addString( "full_access", "full access" ));
    }

}