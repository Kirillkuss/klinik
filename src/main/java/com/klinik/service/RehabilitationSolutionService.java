package com.klinik.service;

import com.klinik.entity.Rehabilitation_solution;
import com.klinik.repositories.RehabilitationSolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class RehabilitationSolutionService {

    @Autowired
    private RehabilitationSolutionRepository repository;

    @PersistenceContext
    private EntityManager em;

    public List<Rehabilitation_solution> getAllReha() throws Exception{
        return repository.findAll();
    }

    public List<Rehabilitation_solution> findByName( String name ) throws Exception{
        return repository.findByName( name );
    }

    public Rehabilitation_solution findById( Long id ) throws Exception{
        return ( Rehabilitation_solution ) em.createQuery( "SELECT e FROM Rehabilitation_solution e WHERE e.id_rehabilitation_solution = :id")
                                             .setParameter( "id", id )
                                             .getResultList().stream().findFirst().orElse( null );
    }

    public Rehabilitation_solution saveRS(Rehabilitation_solution solution) throws Exception{
        return repository.save( solution );
    }
 }
