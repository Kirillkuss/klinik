package com.klinik.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.klinik.entity.Record_patient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseRecordPatient extends BaseResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Record_patient> listRecordPatient;

    public ResponseRecordPatient(){
    }

    public ResponseRecordPatient( Integer code, String message ){
        super( code, message );
    }

    public ResponseRecordPatient( Integer code, String message, List<Record_patient> listRecordPatient){
        super( code, message );
        this.listRecordPatient = listRecordPatient;
    }

    public static ResponseRecordPatient error( int code, Throwable e ){
        return new ResponseRecordPatient( code , null == e.getMessage() ? "System malfunction" : e.getMessage());
    }
    
    
}
