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

    private final TreatmentService              treatmentService;
    private final RehabilitationSolutionService rehabilitationSolutionService;
    private final CardPatientService            cardPatientService;
    private final DoctorService                 doctorService;
    private final DrugService                   serviceDrug;
    public ResponseEntity<List<Treatment>> getAllTreatment() throws Exception{
        return new ResponseEntity<>(treatmentService.allListTreatment(), HttpStatus.OK );
    }
    public ResponseEntity<Treatment> addTreatment( Treatment treatment,
                                                   Long drug_id,
                                                   Long card_patient_id,
                                                   Long rehabilitation_solution_id, 
                                                   Long doctor_id ) throws Exception{
        if( serviceDrug.findById( drug_id) == null )                                          throw new MyException( 400, "Указано неверное значение медикаментозного лечения, укажите другой");
        if( treatmentService.findById( treatment.getId_treatment()) != null  )                throw new MyException( 409, "Лечение с таким ИД уже существует, используйте другой");
        if( rehabilitationSolutionService.findByIdList(rehabilitation_solution_id) == null  ) throw new MyException( 400, "Указано неверное значение реабилитационного лечения, укажите другой");
        if( cardPatientService.findByIdCard(card_patient_id ) == null )                       throw new MyException( 400, "Указано неверное значение карты пациента, укажите другой");
        if( doctorService.findById( doctor_id )  == null )                                    throw new MyException( 400, "Указано неверное значение ид доктора, укажите другой");
        treatment.setCard_patient_id( cardPatientService.findByIdCard(card_patient_id ).getId_card_patient() );
        treatment.setRehabilitation_solution( rehabilitationSolutionService.findByIdList(rehabilitation_solution_id) );
        treatment.setDoctor( doctorService.findById( doctor_id ) );
        treatment.setDrug( serviceDrug.findById( drug_id ));
        return new ResponseEntity<>( treatmentService.addTreatment( treatment ), HttpStatus.CREATED );              
    }
    public ResponseEntity<List<Treatment>> findByParamIdCardAndDateStart( Long id, LocalDateTime dateFrom, LocalDateTime dateTo) throws Exception{
        return new ResponseEntity<>( treatmentService.findByParamIdCardAndDateStart(id, dateFrom, dateTo), HttpStatus.OK );
    }

    public ResponseEntity<List<Treatment>> findByParamIdCardAndIdRh( Long idCard, Long idReSol ) throws Exception{
        return new ResponseEntity<>( treatmentService.findByParamIdCardAndIdRh( idCard, idReSol ), HttpStatus.OK );
    }

}
