package com.klinik.service;

import com.klinik.entity.Rehabilitation_solution;
import com.klinik.repositories.RehabilitationSolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RehabilitationSolutionService {

    @Autowired
    private RehabilitationSolutionRepository rehabilitationSolutionRepository;

    public List<Rehabilitation_solution> getAllReha() throws Exception{
        return rehabilitationSolutionRepository.findAll();
    }

    public Rehabilitation_solution findByName( String name ) throws Exception{
        return rehabilitationSolutionRepository.findByName( name );
    }

    public Rehabilitation_solution saveRS(Rehabilitation_solution solution) throws Exception{
        return rehabilitationSolutionRepository.save( solution );
    }

    public Rehabilitation_solution findByIdList(Long idList ) throws Exception{
        return rehabilitationSolutionRepository.findByIdList( idList );
    }
 }
