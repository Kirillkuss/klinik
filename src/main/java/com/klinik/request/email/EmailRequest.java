package com.klinik.request.email;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequest {

    @Schema( name        = "login",
             description = "login",
             example     = "admin",
             required    = true )
    private String login;

    @Schema( name        = "subject",
             description = "тема сообщения",
             example     = "Изменение пароля",
             required    = true )
    private String subject;

    @Schema( name        = "body",
             description = "сообщение",
             example     = "ваш пароль был изменен: ...",
             required    = true )
    private String body;
}