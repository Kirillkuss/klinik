package com.klinik.controller;

import com.klinik.entity.Rehabilitation_solution;
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
        return new ResponseEntity<>( rehabilitationSolutionService.findByName( name ), HttpStatus.OK );
    }
    public ResponseEntity<Rehabilitation_solution> save( Rehabilitation_solution solution ) throws Exception{
        return new ResponseEntity<>( rehabilitationSolutionService.saveRS( solution ), HttpStatus.CREATED );
    }
}
