package com.klinik.request.email;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequest {

    @Schema( name        = "to",
             description = "От кого",
             example     = "Mur245@gmail.com",
             required    = true )
    private String to;

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