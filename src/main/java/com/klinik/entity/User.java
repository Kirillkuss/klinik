package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "kl_user")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 3317686311392412458L;

    @Id
    @Hidden
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id")
    @Schema( name        = "id",
             description = "id",
             example     = "-1" )
    private Long id;

    @Column( name = "login")
    @Schema( name        = "login",
             description = "admin",
             example     = "admin",
             required    = true )
    @NotNull         
    private String login;

    @Column( name = "password")
    @Schema( name        = "password",
             description = "admin",
             example     = "admin",
             required    = true )
    @NotNull
    private String password;

    @Column( name = "role")
    @Schema( name        = "role",
            description = "роль" )
    private Role role;

    
    @Column( name = "email")
    @Schema( name        = "email",
             description = "email",
             example     = "jseuertne@mail.com",
             required    = true )
    @NotNull
    private String email;

    @Hidden
    @Column( name = "salt")
    private String salt;

    
    @Column( name = "status")
    private Boolean status;

    public User ( String login, String password, Role role, String email , String salt,Boolean status ){
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.salt = salt;
        this.status = status;
    }

}
