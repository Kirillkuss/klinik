package com.klinik.response.report.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Card_patient {

    private String diagnosis; 
    private Boolean allergy; 
    private String note;
    private String сonclusion;
    private Сomplaint complaint;
    private Patient patient;
    private Long count_treatment;

    public Card_patient(){
    }

    public Card_patient(String diagnosis, Boolean allergy,  String note, String сonclusion , Сomplaint complaint, Patient patient, Long count_treatment){
        this.diagnosis = diagnosis;
        this.allergy = allergy;
        this.note = note;
        this.сonclusion = сonclusion;
        this.complaint = complaint;
        this.patient = patient;
        this.count_treatment = count_treatment;
    }
    
}
