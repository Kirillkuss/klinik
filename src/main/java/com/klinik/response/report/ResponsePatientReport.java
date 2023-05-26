package com.klinik.response.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.klinik.response.BaseResponse;
import com.klinik.response.report.models.Card_patient;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class ResponsePatientReport extends BaseResponse {
    
    @JsonInclude(Include.NON_NULL)
    private Card_patient card;

    public ResponsePatientReport(){
        
    }
   
}
