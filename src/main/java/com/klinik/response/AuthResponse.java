package com.klinik.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class AuthResponse {

    @Schema (description = "Код сообщения", name = "Код сообщения",  example = "200")
    private Integer code = 0;
    @Schema (description = "Сообщение", name = "Сообщение",  example = "успешно")
    private String message = "успешно";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema (description = "Токен", name = "Токен")
    private String tocken;
    
}
