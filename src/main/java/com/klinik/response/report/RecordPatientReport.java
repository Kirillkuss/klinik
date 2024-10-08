package com.klinik.response.report;

import java.util.List;

import com.klinik.entity.CardPatient;
import com.klinik.entity.RecordPatient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class RecordPatientReport {

    private CardPatient card;
    private Long countRecordForTime;
    private List<RecordPatient> listRecordPatient;

}
