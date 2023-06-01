package com.klinik.response.report;

import java.util.List;

import com.klinik.entity.Record_patient;
import com.klinik.response.report.models.Card_patient;
import com.klinik.response.report.models.Patient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordPatientReport {

    @JsonInclude(Include.NON_NULL)
    private Card_patient card;
    @JsonInclude(Include.NON_NULL)
    private Long count_record_for_time;
    @JsonInclude(Include.NON_NULL)
    private List<Record_patient> listRecordPatient;

    @Override
    public String toString() {
        return new StringBuilder(" { \n")
                      .append("   Карта пациента: ").append(card).append(",\n")
                      .append("   Количество записей к докторам: ").append(count_record_for_time).append(",\n")
                      .append("   Список записей к докторам: ").append(listRecordPatient).append("\n }\n")
                      .toString();
    }
    
}