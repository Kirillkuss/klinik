package com.klinik.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportDrug {
    
    String name_drug_treatment;
    Long count_drug_treatment;
    Long count_patient;

    public ReportDrug(){ 
    }

    public ReportDrug( String name_drug_treatment,Long count_drug_treatment,Long count_patient  ){ 
        this.name_drug_treatment = name_drug_treatment;
        this.count_drug_treatment = count_drug_treatment;
        this.count_patient = count_patient;
    }

}
