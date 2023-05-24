package com.klinik.response;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.klinik.entity.Patient;

@Getter
@Setter
public class ResponsePatient<T> extends BaseResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Patient> patients;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Patient patient;

    public ResponsePatient(){
    }

    public ResponsePatient( Integer code, String message){
        super( code, message );
    }

    public ResponsePatient( Integer code, String message, List<Patient> patients ){
        super(code, message);
        this.patients = patients;
    }

    public ResponsePatient( Integer code, String message, Patient patient ){
        super(code, message);
        this.patient = patient;
    }

    public static ResponsePatient error( int code, Throwable e ){
        return new ResponsePatient( code , null == e.getMessage() ? "System malfunction" : e.getMessage());
    }
    
}
