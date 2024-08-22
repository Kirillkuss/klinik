package com.klinik.service;

import com.klinik.entity.RehabilitationSolution;
import com.klinik.repositories.RehabilitationSolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RehabilitationSolutionService {

    private final RehabilitationSolutionRepository rehabilitationSolutionRepository;

    public List<RehabilitationSolution> getAll() {
        return rehabilitationSolutionRepository.findAll();
    }
    public RehabilitationSolution findByName( String name ){
        return rehabilitationSolutionRepository.findByName( name )
                                               .orElseThrow(() ->new NoSuchElementException("Ребилитационное лечение c таким наименованием не существует"));
    }
    public RehabilitationSolution saveRehabilitationSolution(RehabilitationSolution solution) throws Exception{
        solution.setIdRehabilitationSolution( -1L );
        checkSaveRehabilitationSolution( solution );
        return rehabilitationSolutionRepository.save( solution );
    }

    private void checkSaveRehabilitationSolution( RehabilitationSolution solution ){
        if( rehabilitationSolutionRepository.findByName( solution.getName() ).isPresent()) throw new IllegalArgumentException( "Ребилитационное лечение с таким наименованием уже существует");
        if( rehabilitationSolutionRepository.findById( solution.getIdRehabilitationSolution() ).isPresent() ) throw new IllegalArgumentException( "Ребилитационное лечение с таким ИД уже существует");
    }
 }
