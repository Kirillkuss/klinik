package com.klinik.response.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class ResponseReport {

    private String nameRehabilitationTreatment;
    private Long   countTreatment;

    @JsonInclude(Include.NON_NULL)
    private Long countPatient;
    public ResponseReport(){
    }
    public ResponseReport( String nameRehabilitationTreatment,Long countTreatment, Long countPatient){
        this.nameRehabilitationTreatment = nameRehabilitationTreatment;
        this.countTreatment = countTreatment;
        this.countPatient = countPatient;
    }
}
