package com.klinik.request;

import jakarta.persistence.Column;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

    @Column( name = "login")
    @Schema( name        = "login",
             description = "login",
             example     = "admin",
             required    = true )
    private String login;
    
    @Column( name = "password")
    @Schema( name        = "password",
             description = "password",
             example     = "admin",
             required    = true )
    private String password;
    
}
