package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User implements Serializable {

    private static final long serialVersionUID = 3317686311392412458L;

    @Schema( name        = "username",
            description = "admin",
            example     = "admin" )
    private String username;
    @Schema( name        = "password",
            description = "admin",
            example     = "admin" )
    private String password;
    @Hidden
    private String role;
    @Hidden
    private String email;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
