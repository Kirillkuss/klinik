package com.klinik.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.klinik.entity.CardPatient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCardPatientByDocument extends BaseResponse{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CardPatient cardPatient;

    public ResponseCardPatientByDocument(){

    }

    public ResponseCardPatientByDocument( Integer code, String message ){
        super( code, message );
    }

    public ResponseCardPatientByDocument( Integer code, String message, CardPatient cardPatient ){
        super( code, message);
        this.cardPatient = cardPatient;
    }

    public static ResponseCardPatientByDocument error( int code, Throwable e ){
        return new ResponseCardPatientByDocument( code , null == e.getMessage() ? "System malfunction" : e.getMessage());
    }
}
