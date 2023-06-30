package com.klinik.controller;

import com.klinik.entity.Rehabilitation_solution;
import com.klinik.excep.MyException;
import com.klinik.rest.IRehabilitationSolution;
import com.klinik.service.RehabilitationSolutionService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RehabilitationSolutionController implements IRehabilitationSolution {

    private final RehabilitationSolutionService rehabilitationSolutionService;
    public ResponseEntity<List<Rehabilitation_solution>> getAllRehabilitationSolution() throws Exception{
        return new ResponseEntity<>( rehabilitationSolutionService.getAllReha(), HttpStatus.OK );
    }
    public ResponseEntity<Rehabilitation_solution> findByName( String name ) throws Exception{
        if( rehabilitationSolutionService.findByName( name ) == null ) throw new MyException( 404, "По данному запросу ничего не найдено");
        return new ResponseEntity<>( rehabilitationSolutionService.findByName( name ), HttpStatus.OK );
    }
    public ResponseEntity<Rehabilitation_solution> save( Rehabilitation_solution solution ) throws Exception{
        if( rehabilitationSolutionService.findByName( solution.getName() ) != null )                         throw new MyException( 409, "Ребилитационное лечение с таким наименованием уже существует");
        if( rehabilitationSolutionService.findByIdList( solution.getId_rehabilitation_solution() ) != null ) throw new MyException( 409, "Ребилитационное лечение с таким ИД уже существует");
        return new ResponseEntity<>( rehabilitationSolutionService.saveRS( solution ), HttpStatus.CREATED );
    }
}
