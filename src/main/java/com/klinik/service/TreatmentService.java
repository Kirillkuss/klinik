package com.klinik.service;

import com.klinik.entity.Treatment;
import com.klinik.repositories.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class TreatmentService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TreatmentRepository repository;
    /**
     * Получение списка всех лечений 
     * @return List<Treatment>
     * @throws Exception
     */
    public List<Treatment> allListTreatment() throws Exception{
        return repository.findAll();
    }
    /**
     * Добавить лечение пациента
     * @param treatment - сущность Лечение ы
     * @return Treatment
     * @throws Exception
     */
    public Treatment addTreatment( Treatment treatment ) throws Exception{
        return repository.save( treatment );
    }
    /**
     * Поиск лечения по ИД лечения 
     * @param id - ИД лечения
     * @return Treatment
     * @throws Exception
     */
    public Treatment findById( Long id ) throws Exception{
        return ( Treatment ) em.createQuery( "SELECT u FROM Treatment u WHERE u.id_treatment = :id" )
                               .setParameter( "id", id )
                               .getResultList()
                               .stream().findFirst().orElse( null );
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
        return repository.findByParamIdCardAndDateStart(id, dateFrom, dateTo);
    }
    /**
     * Получение списка лечений пациентов по параметрам
     * @param idCard  - Ид карты пациента
     * @param idReSol - Ид реабилитационного лечения
     * @return List<Treatment>
     * @throws Exception
     */
    public List<Treatment> findByParamIdCardAndIdRh( Long idCard, Long idReSol ) throws Exception{
        return repository.findByParamIdCardAndIdRh(idCard, idReSol);
    }
}
