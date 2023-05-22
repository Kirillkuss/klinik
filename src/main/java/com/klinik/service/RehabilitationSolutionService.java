package com.klinik.service;

import com.klinik.entity.Rehabilitation_solution;
import com.klinik.repositories.RehabilitationSolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RehabilitationSolutionService {

    @Autowired
    private RehabilitationSolutionRepository repository;

    public List<Rehabilitation_solution> getAllReha() throws Exception{
        return repository.findAll();
    }

    public Rehabilitation_solution findByName( String name ) throws Exception{
        return repository.findByName( name );
    }
 }
