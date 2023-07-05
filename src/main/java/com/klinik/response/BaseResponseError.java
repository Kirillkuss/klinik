package com.klinik.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseResponseError {

    @Schema (description = "Код сообщения", name = "code",  example = "400")
    private Integer code;
    @Schema (description = "Сообщение", name = "message",  example = "Сообщение об ошибке")
    private String massage;
    public BaseResponseError(){
    }
    public BaseResponseError(Integer code, String massage){
        this.code = code;
        this.massage = massage;
    }

    
}
