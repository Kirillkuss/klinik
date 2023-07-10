package com.klinik.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.klinik.entity.Treatment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseTreatment  {

    public List<Treatment> response;
    public Treatment treatment;
}
