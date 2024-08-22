package com.klinik.response;

import com.klinik.entity.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserResponse {

    @Schema( name        = "login",
             description = "Логин",
             example     = "admin",
             required    = true )
    private String login;
    @Schema( name        = "mail",
             description = "Почта",
             example     = "mouse@gmail.com",
             required    = true )
    private String mail;
    @Schema( name        = "role",
             description = "Роль",
             example     = "ADMIN",
             required    = true )
    private Role role;
    @Schema( name        = "statusBlock",
             description = "Статус блокировки",
             example     = "false",
             required    = true )
    private Boolean statusBlock;
    
}
