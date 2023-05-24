package com.klinik.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema (description = "Сообщение", name = "message",  example = "success")
    private T response;

    public BaseResponse(){
    }

    public BaseResponse(Integer code, String massage){
        this.code = code;
        this.massage = massage;
    }

    public BaseResponse(Integer code, String massage, T response){
        this.code = code;
        this.massage = massage;
        this.response = response;
    }

    public static BaseResponse success() {
        return new BaseResponse( 0, "success");
    }
    
    public static BaseResponse error( int code, Throwable e ){
        return new BaseResponse( code , null == e.getMessage() ? "System malfunction" : e.getMessage());
    }
}
