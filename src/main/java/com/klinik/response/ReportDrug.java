package com.klinik.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportDrug {
    
    private String nameDrugTreatment;
    private Long countDrugTreatment;
    private Long countPatient;

    public ReportDrug(){ 
    }

    public ReportDrug( String nameDrugTreatment,Long countDrugTreatment,Long countPatient ){ 
        this.nameDrugTreatment = nameDrugTreatment;
        this.countDrugTreatment = countDrugTreatment;
        this.countPatient = countPatient;
    }
}
