package com.klinik.service;

import com.klinik.entity.RehabilitationSolution;
import com.klinik.excep.MyException;
import com.klinik.repositories.RehabilitationSolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RehabilitationSolutionService {

    private final RehabilitationSolutionRepository rehabilitationSolutionRepository;

    public List<RehabilitationSolution> getAll() {
        return rehabilitationSolutionRepository.findAll();
    }

    public RehabilitationSolution findByName( String name ) throws Exception{
        return rehabilitationSolutionRepository.findByName( name ).orElseThrow();
    }

    public RehabilitationSolution saveRS(RehabilitationSolution solution) throws Exception{
        if( rehabilitationSolutionRepository.findByName( solution.getName() ).isPresent() == true ) throw new MyException( 409, "Ребилитационное лечение с таким наименованием уже существует");
        if( rehabilitationSolutionRepository.findById( solution.getIdRehabilitationSolution() ).isPresent() == true ) throw new MyException( 409, "Ребилитационное лечение с таким ИД уже существует");
        return rehabilitationSolutionRepository.save( solution );
    }
 }
