package com.klinik.controller;

import com.klinik.entity.Record_patient;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.rest.IRecordPatinet;
import com.klinik.service.CardPatientService;
import com.klinik.service.DoctorService;
import com.klinik.service.RecordPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
public class RecordPatientController implements IRecordPatinet {

    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired
    private RecordPatientService recordPatientService;
    @Autowired
    private DoctorService        serviceDoctor;
    @Autowired
    private CardPatientService   servicePatientCard;

    public BaseResponse allListRecordPatient() throws Exception, MyException{
        return new BaseResponse<>( 200, "success", recordPatientService.allListRecordPatient() );
    }
    public BaseResponse addRecordPatient( Record_patient record_patient, Long doctor_id, Long card_patient_id ) throws Exception, MyException{
        if ( serviceDoctor.findById( doctor_id ) == null )                          throw new MyException( 440, "Нет доктора с таким идентификатором");
        if ( servicePatientCard.findByIdCard( card_patient_id ) == null )           throw new MyException( 441, "Нет карты пациента с таким идентификатором");
        if ( recordPatientService.findById( record_patient.getId_record()) != null) throw new MyException( 442, "Запись к врачу с таким ИД уже существует, установите другой ИД записи к врачу");
        record_patient.setDoctor(serviceDoctor.findById( doctor_id ));;
        record_patient.setCard_patient_id( card_patient_id );
        return new BaseResponse<>( 200, "success", recordPatientService.saveRecordPatient( record_patient));                
    }

    public BaseResponse findByParams( Long id, LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception, MyException{
        return new BaseResponse<>( 200, "success", recordPatientService.findByParam(id, dateFrom, dateTo));
    }

    
}
