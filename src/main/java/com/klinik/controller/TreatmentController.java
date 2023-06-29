package com.klinik.controller;

import com.klinik.entity.Treatment;
import com.klinik.excep.MyException;
import com.klinik.response.ResponseTreatment;
import com.klinik.rest.ITreatment;
import com.klinik.service.CardPatientService;
import com.klinik.service.DoctorService;
import com.klinik.service.DrugService;
import com.klinik.service.RehabilitationSolutionService;
import com.klinik.service.TreatmentService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TreatmentController implements ITreatment{

    @ExceptionHandler(Throwable.class)
    public ResponseTreatment errBaseResponse( Throwable ex ){
        return ResponseTreatment.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public ResponseTreatment errBaseResponse( MyException ex ){
        return ResponseTreatment.error( ex.getCode(), ex );
    }

    @Autowired private TreatmentService              treatmentService;
    @Autowired private RehabilitationSolutionService rehabilitationSolutionService;
    @Autowired private CardPatientService            cardPatientService;
    @Autowired private DoctorService                 doctorService;
    @Autowired private DrugService                   serviceDrug;
    public ResponseTreatment getAllTreatment() throws Exception{
        return new ResponseTreatment( 200, "успешно", treatmentService.allListTreatment());
    }
    public ResponseTreatment addTreatment( Treatment treatment,
                                           Long drug_id,
                                           Long card_patient_id,
                                           Long rehabilitation_solution_id, 
                                           Long doctor_id ) throws Exception{
        if( serviceDrug.findById( drug_id) == null )                                          throw new MyException( 474, "Указано неверное значение медикаментозного лечения, укажите другой");
        if( treatmentService.findById( treatment.getId_treatment()) != null  )                throw new MyException( 470, "Лечение с таким ИД уже существует, используйте другой");
        if( rehabilitationSolutionService.findByIdList(rehabilitation_solution_id) == null  ) throw new MyException( 471, "Указано неверное значение реабилитационного лечения, укажите другой");
        if( cardPatientService.findByIdCard(card_patient_id ) == null )                       throw new MyException( 472, "Указано неверное значение карты пациента, укажите другой");
        if( doctorService.findById( doctor_id )  == null )                                    throw new MyException( 473, "Указано неверное значение ид доктора, укажите другой");
        treatment.setCard_patient_id( cardPatientService.findByIdCard(card_patient_id ).getId_card_patient() );
        treatment.setRehabilitation_solution( rehabilitationSolutionService.findByIdList(rehabilitation_solution_id) );
        treatment.setDoctor( doctorService.findById( doctor_id ) );
        treatment.setDrug( serviceDrug.findById( drug_id ));
        return new ResponseTreatment( 200, "success", treatmentService.addTreatment( treatment ));              
    }
    public ResponseTreatment findByParamIdCardAndDateStart( Long id, LocalDateTime dateFrom, LocalDateTime dateTo) throws Exception{
        return new ResponseTreatment( 200, "success", treatmentService.findByParamIdCardAndDateStart(id, dateFrom, dateTo));
    }

    public ResponseTreatment findByParamIdCardAndIdRh( Long idCard, Long idReSol ) throws Exception{
        return new ResponseTreatment( 200, "success", treatmentService.findByParamIdCardAndIdRh( idCard, idReSol ));
    }

}
