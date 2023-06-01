package com.klinik.response.report.models;

import java.util.List;

import com.klinik.entity.Сomplaint;
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
    private List<String> complaints;
    @JsonInclude(Include.NON_NULL)
    private Patient patient;
    @JsonInclude(Include.NON_NULL)
    private Long count_rehabilitation_treatment;
    @JsonInclude(Include.NON_NULL)
    private List<ResponseReport> full_info_rehabilitation_treatment;

    public Card_patient(){
    }

    public Card_patient(String diagnosis, Boolean allergy,  String note, String сonclusion , List<String> complaints, Patient patient, Long count_rehabilitation_treatment, List<ResponseReport> full_info_rehabilitation_treatment){
        this.diagnosis = diagnosis;
        this.allergy = allergy;
        this.note = note;
        this.сonclusion = сonclusion;
        this.complaints = complaints;
        this.patient = patient;
        this.count_rehabilitation_treatment = count_rehabilitation_treatment;
        this.full_info_rehabilitation_treatment = full_info_rehabilitation_treatment;
    }


    @Override
    public String toString() {
        return new StringBuilder(" { \n")
                      .append("     1. Диагноз: ").append(diagnosis).append(",\n")  
                      .append("     2. Аллергия: ").append(allergy == true ? "Да" : "Нет").append(",\n")
                      .append("     3. Примечание: ").append(note == null ? "" : note).append(",\n")
                      .append("     4. Заключение: ").append(сonclusion == null ? "" : сonclusion).append(",\n")
                      .append("     5. Список жалоб: ").append(complaints).append(",\n")
                      .append("     6. Пациент: ").append(patient).append(",\n")
                      .append("     7. Количество реабилитационных лечений: ").append(count_rehabilitation_treatment == null ? "" : count_rehabilitation_treatment).append(",\n")
                      .append("       8. Информация по реабилитационным лечениям: ").append(full_info_rehabilitation_treatment == null ? "" : full_info_rehabilitation_treatment).append("\n }\n")
                      .toString();
    }
    
}
