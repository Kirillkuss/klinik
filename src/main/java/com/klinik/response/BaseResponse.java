package com.klinik.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BaseResponse<T> {

    @Schema (description = "Код сообщения", name = "code",  example = "200")
    private Integer code = 200;
    @Schema (description = "Сообщение", name = "message",  example = "success")
    private String massage = "success";

    public BaseResponse(){
    }

    public BaseResponse(Integer code, String massage){
        this.code = code;
        this.massage = massage;
    }

    public static BaseResponse success() {
        return new BaseResponse( 0, "success");
    } 
}
