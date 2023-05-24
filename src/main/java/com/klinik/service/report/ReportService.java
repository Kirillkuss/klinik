package com.klinik.service.report;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.klinik.response.BaseResponse;


@Service
public class ReportService {

    @PersistenceContext
    private EntityManager em;
    
    public BaseResponse getStatReport(Long id) throws Exception{
        BaseResponse response = new BaseResponse<>( 200, "success");
        try{
            response.setResponse(em.createQuery("SELECT COUNT( u.rehabilitation_solution.id_rehabilitation_solution ) FROM Treatment u where u.rehabilitation_solution.id_rehabilitation_solution = :id").setParameter("id", id).getResultList());
            return response;
        }catch( Exception ex ){
            return BaseResponse.error( 999 , ex );
        }
    }
    
}
