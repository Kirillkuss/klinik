package com.klinik.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klinik.entity.Drug;
import com.klinik.repositories.DrugRepository;

@Service
public class DrugService {

    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private DrugRepository repository;

    public List<Drug> findAll() throws Exception{
        return repository.findAll();
    }

    public Drug findById( Long id ) throws Exception{
        return repository.findById(id).stream().findFirst().orElse( null );
    }
    
    public Drug saveDrug( Drug drug ) throws Exception{
        return repository.save( drug );
    }

    public Drug findByName( String word ) throws Exception{
        return repository.findByName( word );
    }

    public List<Drug> findByIdDrugTreatment( Long id  ){
        return ( List<Drug>) em.createQuery( "SELECT e FROM Drug e WHERE e.drugTreatment.id_drug = :id")
                               .setParameter("id", id)
                               .getResultList(); 
    }
}