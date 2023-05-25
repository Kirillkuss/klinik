package com.klinik.response.report;


import com.klinik.response.report.models.Card_patient;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class ResponsePatientReport {
    
    private Card_patient card;
    
}
