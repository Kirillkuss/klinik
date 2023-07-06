package com.klinik.service;

import com.klinik.entity.CardPatient;
import com.klinik.entity.Doctor;
import com.klinik.entity.Drug;
import com.klinik.entity.RehabilitationSolution;
import com.klinik.entity.Treatment;
import com.klinik.excep.MyException;
import com.klinik.repositories.CardPatientRepository;
import com.klinik.repositories.DoctorRerository;
import com.klinik.repositories.DrugRepository;
import com.klinik.repositories.RehabilitationSolutionRepository;
import com.klinik.repositories.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TreatmentService {

    private final TreatmentRepository              treatmentRepository;
    private final DrugRepository                   drugRepository;
    private final RehabilitationSolutionRepository rehabilitationSolutionRepository;
    private final CardPatientRepository            cardPatientRepository;
    private final DoctorRerository                 doctorRerository;
    /**
     * Получение списка всех лечений 
     * @return List<Treatment>
     * @throws Exception
     */
    public List<Treatment> findAll() {
        return treatmentRepository.findAll();
    }
    /**
     * Добавить лечение пациента
     * @param treatment - сущность Лечение ы
     * @return Treatment
     * @throws Exception
     */
    public Treatment addTreatment( Treatment treatment, Long idDrug, Long idCardPatient,
                                   Long idRehabilitationSolution, Long idDoctor ) throws Exception{
        Optional<Drug> drug = drugRepository.findById( idDrug);
        Optional<Doctor> doctor = doctorRerository.findById( idDoctor );
        Optional<RehabilitationSolution> rehabilitationSolution = rehabilitationSolutionRepository.findById( idRehabilitationSolution );
        Optional<CardPatient> cardPatietn = cardPatientRepository.findById( idCardPatient );
        if( drug.isEmpty() ) throw new MyException( 400, "Указано неверное значение медикаментозного лечения, укажите другой");
        if( treatmentRepository.findById( treatment.getId_treatment()).isPresent() ) throw new MyException( 409, "Лечение с таким ИД уже существует, используйте другой");
        if( rehabilitationSolution.isEmpty()) throw new MyException( 400, "Указано неверное значение реабилитационного лечения, укажите другой");
        if( cardPatietn.isEmpty() ) throw new MyException( 400, "Указано неверное значение карты пациента, укажите другой");
        if( doctor.isEmpty() ) throw new MyException( 400, "Указано неверное значение ид доктора, укажите другой");
        treatment.setCard_patient_id( cardPatietn.get().getIdCardPatient() );
        treatment.setRehabilitation_solution( rehabilitationSolution.get() );
        treatment.setDoctor( doctor.get() );
        treatment.setDrug( drug.get() );
        return treatmentRepository.save( treatment );
    }
    /**
     * Получение списка лечений пациентов по параметрам
     * @param id       - Ид карты пациента
     * @param dateFrom - Дата лечения пациента с 
     * @param dateTo   - Дата лечение пациента по
     * @return List<Treatment>
     * @throws Exception
     */
    public List<Treatment> findByParamIdCardAndDateStart( Long id, LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception{
        List<Treatment> response = treatmentRepository.findByParamIdCardAndDateStart(id, dateFrom, dateTo);
        if( response.isEmpty() ) throw new MyException( 404, "По данному запросу ничего не найдено");
        return response;
    }
    /**
     * Получение списка лечений пациентов по параметрам
     * @param idCard  - Ид карты пациента
     * @param idReSol - Ид реабилитационного лечения
     * @return List<Treatment>
     * @throws Exception
     */
    public List<Treatment> findByParamIdCardAndIdRh( Long idCard, Long idReSol ) throws Exception{
        List<Treatment> response = treatmentRepository.findByParamIdCardAndIdRh(idCard, idReSol);
        if ( response.isEmpty()) throw new MyException( 404, "По данному запросу ничего не найдено");
        return response;
    }
}
