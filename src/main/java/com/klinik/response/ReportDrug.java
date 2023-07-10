package com.klinik.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class ReportDrug {
    
    String nameDrugTreatment;
    Long countDrugTreatment;
    Long countPatient;
}
