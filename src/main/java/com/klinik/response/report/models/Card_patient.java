package com.klinik.response.report.models;

import java.util.List;

import com.klinik.response.report.ResponseReport;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
    @JsonInclude(Include.NON_NULL)
    private List<Сomplaint> complaints;
    @JsonInclude(Include.NON_NULL)
    private Patient patient;
    @JsonInclude(Include.NON_NULL)
    private Long count_rehabilitation_treatment;
    @JsonInclude(Include.NON_NULL)
    private List<ResponseReport> full_info_rehabilitation_treatment;

    public Card_patient(){
    }

    public Card_patient(String diagnosis, Boolean allergy,  String note, String сonclusion , List<Сomplaint> complaints, Patient patient, Long count_rehabilitation_treatment, List<ResponseReport> full_info_rehabilitation_treatment){
        this.diagnosis = diagnosis;
        this.allergy = allergy;
        this.note = note;
        this.сonclusion = сonclusion;
        this.complaints = complaints;
        this.patient = patient;
        this.count_rehabilitation_treatment = count_rehabilitation_treatment;
        this.full_info_rehabilitation_treatment = full_info_rehabilitation_treatment;
    }
    
}
