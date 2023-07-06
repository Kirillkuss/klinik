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

    private String name_rehabilitation_treatment;
    private Long   count_treatment;

    @JsonInclude(Include.NON_NULL)
    private Long count_patient;
    public ResponseReport(){
    }
    public ResponseReport( String name_rehabilitation_treatment,Long count_treatment, Long count_patient){
        this.name_rehabilitation_treatment = name_rehabilitation_treatment;
        this.count_treatment = count_treatment;
        this.count_patient = count_patient;
    }
}
