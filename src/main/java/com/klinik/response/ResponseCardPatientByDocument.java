package com.klinik.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.klinik.entity.CardPatient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseCardPatientByDocument {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CardPatient cardPatient;
}
