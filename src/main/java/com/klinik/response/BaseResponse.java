package com.klinik.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@EqualsAndHashCode
public class BaseResponse<T> {

    @Schema (description = "Код сообщения", name = "Код сообщения",  example = "200")
    private Integer code = 200;
    @Schema (description = "Сообщение", name = "Сообщение",  example = "успешно")
    private String massage = "успешно";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema (description = "Ответ", name = "response")
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
        return new BaseResponse( 0, "успешно");
    }
    
    public static BaseResponse error( Integer code, Throwable e ){
        return new BaseResponse( code , null == e.getMessage() ? "Сообщение об ошибке" : e.getMessage());
    }

    @Override
    public String toString() {
        return new StringBuilder(" { \n")
                      .append("   Код: ").append(code).append(",\n")  
                      .append("   Сообщение: ").append(massage).append(",\n")
                      .append("   Ответ: ").append("\n").append(response == null ? "" : response).append("\n }\n")
                      .toString();
    }

}
