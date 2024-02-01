package com.klinik.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BaseResponse<T> implements Serializable {

    @Schema (description = "Код сообщения", name = "Код сообщения",  example = "200")
    private Integer code = 0;
    @Schema (description = "Сообщение", name = "Сообщение",  example = "успешно")
    private String message = "успешно";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema (description = "Ответ", name = "response")
    private T response;

    public BaseResponse(Integer code, String massage){
        this.code = code;
        this.message = massage;
    }

    public static BaseResponse success() {
        return new BaseResponse( 200, "success");
    }
    
    public static BaseResponse error( Integer code, Throwable e ){
        return new BaseResponse( code , null == e.getMessage() ? "Сообщение об ошибке" : e.getMessage());
    }

}
