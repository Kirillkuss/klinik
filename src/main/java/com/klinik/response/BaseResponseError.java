package com.klinik.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class BaseResponseError {

    @Schema (description = "Код сообщения", name = "code",  example = "400")
    private Integer code;
    @Schema (description = "Сообщение", name = "message",  example = "Сообщение об ошибке")
    private String massage; 
}
