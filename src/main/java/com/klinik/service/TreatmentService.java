package com.klinik.service;

import com.klinik.entity.Treatment;
import com.klinik.repositories.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentService {

    private final TreatmentRepository treatmentRepository;
    /**
     * Получение списка всех лечений 
     * @return List<Treatment>
     * @throws Exception
     */
    public List<Treatment> allListTreatment() throws Exception{
        return treatmentRepository.findAll();
    }
    /**
     * Добавить лечение пациента
     * @param treatment - сущность Лечение ы
     * @return Treatment
     * @throws Exception
     */
    public Treatment addTreatment( Treatment treatment ) throws Exception{
        return treatmentRepository.save( treatment );
    }
    /**
     * Поиск лечения по ИД лечения 
     * @param id - ИД лечения
     * @return Treatment
     * @throws Exception
     */
    public Treatment findById( Long id ) throws Exception{
        return treatmentRepository.findByIdTreatment( id );
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
        return treatmentRepository.findByParamIdCardAndDateStart(id, dateFrom, dateTo);
    }
    /**
     * Получение списка лечений пациентов по параметрам
     * @param idCard  - Ид карты пациента
     * @param idReSol - Ид реабилитационного лечения
     * @return List<Treatment>
     * @throws Exception
     */
    public List<Treatment> findByParamIdCardAndIdRh( Long idCard, Long idReSol ) throws Exception{
        return treatmentRepository.findByParamIdCardAndIdRh(idCard, idReSol);
    }
}
