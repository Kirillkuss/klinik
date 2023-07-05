package com.klinik.service;

import com.klinik.entity.Rehabilitation_solution;
import com.klinik.excep.MyException;
import com.klinik.repositories.RehabilitationSolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RehabilitationSolutionService {

    private final RehabilitationSolutionRepository rehabilitationSolutionRepository;

    public List<Rehabilitation_solution> getAllReha() throws Exception{
        return rehabilitationSolutionRepository.findAll();
    }

    public Rehabilitation_solution findByName( String name ) throws Exception{
        return rehabilitationSolutionRepository.findByName( name ).orElseThrow();
    }

    public Rehabilitation_solution saveRS(Rehabilitation_solution solution) throws Exception{
        if( rehabilitationSolutionRepository.findByName( solution.getName() ).isPresent() == true ) throw new MyException( 409, "Ребилитационное лечение с таким наименованием уже существует");
        if( rehabilitationSolutionRepository.findById( solution.getId_rehabilitation_solution() ).isPresent() == true ) throw new MyException( 409, "Ребилитационное лечение с таким ИД уже существует");
        return rehabilitationSolutionRepository.save( solution );
    }
 }
