package com.klinik.controller;

import com.klinik.entity.Treatment;
import com.klinik.excep.MyException;
import com.klinik.rest.ITreatment;
import com.klinik.service.CardPatientService;
import com.klinik.service.DoctorService;
import com.klinik.service.DrugService;
import com.klinik.service.RehabilitationSolutionService;
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
    public ResponseEntity<Treatment> addTreatment( Treatment treatment, Long idDrug, Long idCardPatient,
                                                   Long idRehabilitationSolution, Long idDoctor ) throws Exception{
        return new ResponseEntity<>( treatmentService.addTreatment( treatment, idDrug, idCardPatient, idRehabilitationSolution, idDoctor ), HttpStatus.CREATED );              
    }
    public ResponseEntity<List<Treatment>> findByParamIdCardAndDateStart( Long id, LocalDateTime dateFrom, LocalDateTime dateTo) throws Exception{
        return new ResponseEntity<>( treatmentService.findByParamIdCardAndDateStart(id, dateFrom, dateTo), HttpStatus.OK );
    }
    public ResponseEntity<List<Treatment>> findByParamIdCardAndIdRh( Long idCard, Long idRehabilitationSolution ) throws Exception{
        return new ResponseEntity<>( treatmentService.findByParamIdCardAndIdRh( idCard, idRehabilitationSolution ), HttpStatus.OK );
    }
}
