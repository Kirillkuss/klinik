package com.klinik.controller.model;

import com.klinik.entity.Treatment;
import com.klinik.request.RequestTreatment;
import com.klinik.rest.model.ITreatment;
import com.klinik.service.TreatmentService;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TreatmentController implements ITreatment{

    private final TreatmentService treatmentService;
    public ResponseEntity<List<Treatment>> getAllTreatment() throws Exception{
        return new ResponseEntity<>(treatmentService.findAll(), HttpStatus.OK );
    }
    public ResponseEntity<Treatment> addTreatment( RequestTreatment requestTreatment ) throws Exception{
        return new ResponseEntity<>( treatmentService.addTreatment( requestTreatment ), HttpStatus.CREATED );              
    }
    public ResponseEntity<List<Treatment>> findByParamIdCardAndDateStart( Long id, LocalDateTime dateFrom, LocalDateTime dateTo) throws Exception{
        return new ResponseEntity<>( treatmentService.findByParamIdCardAndDateStart(id, dateFrom, dateTo), HttpStatus.OK );
    }
    public ResponseEntity<List<Treatment>> findByParamIdCardAndIdRh( Long idCard, Long idRehabilitationSolution ) throws Exception{
        return new ResponseEntity<>( treatmentService.findByParamIdCardAndIdRh( idCard, idRehabilitationSolution ), HttpStatus.OK );
    }
}
