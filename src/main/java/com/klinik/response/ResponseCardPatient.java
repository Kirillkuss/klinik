package com.klinik.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.klinik.entity.Card_patient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseCardPatient extends BaseResponse{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Card_patient> listCardPatient;

    public ResponseCardPatient(){

    }

    public ResponseCardPatient( Integer code, String message ){
        super( code, message );
    }
    public ResponseCardPatient( Integer code, String message, List<Card_patient> listCardPatient ){
        super( code, message);
        this.listCardPatient = listCardPatient;
    }

    public static ResponseCardPatient error( int code, Throwable e ){
        return new ResponseCardPatient( code , null == e.getMessage() ? "System malfunction" : e.getMessage());
    }
}
